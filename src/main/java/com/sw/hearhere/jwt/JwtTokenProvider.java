package com.sw.hearhere.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.hearhere.security.CustomUserDetails;
import com.sw.hearhere.security.UserPrincipalDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private Key key;


    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret.key}") String secret,
                            @Value("${jwt.token-validity-in-seconds}") long accessTokenValidityInSeconds,
                            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInMilliseconds
                            ) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
    }

    @PostConstruct
    public void setKey(){ //jwt.secret을 이요하여 key생성
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //토큰 발급
    public TokensDto generateTokens(Authentication authentication) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        UserPrincipalDto userPrincipalDto = UserPrincipalDto.from((CustomUserDetails) authentication.getPrincipal());
        String subject = objectMapper.writeValueAsString(userPrincipalDto);

        Claims claims = Jwts.claims();
        claims.setSubject(subject);
        claims.put(AUTHORITIES_KEY, authorities);
        String accessToken = createAccessToken(claims);
        String refreshToken = createRefreshToken(claims);
        return new TokensDto(accessToken, refreshToken);
    }

    //accessToken 생성
    private String createAccessToken(Claims claims) {
        long now  = new Date().getTime(); //밀리세컨드
        Date accessTokenValidity = new Date(now + this.accessTokenValidityInMilliseconds);
        claims.setExpiration(accessTokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //refreshToken 생성
    private String createRefreshToken(Claims claims) {
        long now  = new Date().getTime(); //밀리세컨드
        Date refreshTokenValidity = new Date(now + this.refreshTokenValidityInMilliseconds);
        claims.setExpiration(refreshTokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //token에서 Athentication 추출
    public Authentication getAuthentication(String token) throws JsonProcessingException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        String subject = claims.getSubject();
        ObjectMapper objectMapper = new ObjectMapper();
        UserPrincipalDto userPrincipalDto = objectMapper.readValue(subject, UserPrincipalDto.class);
        CustomUserDetails principal = new CustomUserDetails(userPrincipalDto.getId(),userPrincipalDto.getEmail(),  "",
                authorities.get(0));

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    //토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            log.error("만료된 토큰입니다.");
        }
        catch (Exception e) {
            log.error("유효하지 않은 토큰입니다.");
        }
        return false;
    }

    // 토큰 만료 시간 확인(밀리세컨드)
    public Long getExpiration(String accessToken) {
        Date expiration = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();

        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}
