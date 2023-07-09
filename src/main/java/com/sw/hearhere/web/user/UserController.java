package com.sw.hearhere.web.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sw.hearhere.domain.repository.UserRepository;
import com.sw.hearhere.service.UserService;
import com.sw.hearhere.utils.SecurityUtil;
import com.sw.hearhere.web.user.dto.UserReqDto.KakaoUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sw.hearhere.web.user.dto.UserResDto.KakaoLogin;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    /**
     * 카카오 로그인
     */
    @Operation(summary = "카카오 로그인", description = "카카오 로그인")
    @PostMapping("/login/kakao")
    public ResponseEntity<KakaoLogin> kakaoLogin(@Valid @RequestBody KakaoUserInfo kakaoUserInfo) throws JsonProcessingException {
        KakaoLogin kakaoLogin = userService.loginByKakao(kakaoUserInfo.getId(), kakaoUserInfo.getEmail());
        return ResponseEntity.ok(kakaoLogin);
    }


}
