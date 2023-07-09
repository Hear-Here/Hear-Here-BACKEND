package com.sw.hearhere.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sw.hearhere.domain.entity.User;
import com.sw.hearhere.domain.enums.AuthState;
import com.sw.hearhere.domain.enums.Role;
import com.sw.hearhere.domain.repository.UserRepository;
import com.sw.hearhere.jwt.JwtTokenProvider;
import com.sw.hearhere.jwt.TokensDto;
import com.sw.hearhere.web.user.dto.UserResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResDto.KakaoLogin loginByKakao(Long providerId, String email) throws JsonProcessingException {
        String pwd = "kakao_" + providerId;
        Optional<User> user =
                userRepository.findByProviderId(providerId);
        if (user.isPresent()) { //이미 가입된 유저
            return UserResDto.KakaoLogin.fromTokens(AuthState.LOGIN, generateJwtTokens(user.get().getEmail(), pwd));
        } else { // 가입해야하는 유저
            String encodedPwd = passwordEncoder.encode(pwd);
            User newUser = User.builder().providerId(providerId).email(email).pwd(encodedPwd)
                    .nickname("닉넴없음").role(Role.USER).build();
            userRepository.save(newUser);
            return UserResDto.KakaoLogin.fromTokens(AuthState.JOIN, generateJwtTokens(newUser.getEmail(), pwd));
        }
    }

    public TokensDto generateJwtTokens(String email, String pwd) throws JsonProcessingException {
        // 1. email/pwd 를 기반으로 Authentication 객체 생성
        //    이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, pwd);

        // 2. 실제 검증 (사용자 비밀번호 체크)
        //    authenticate 메서드 실행 => CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 바탕으로 JWT 토큰 생성
        return jwtTokenProvider.generateTokens(authentication);
    }
}
