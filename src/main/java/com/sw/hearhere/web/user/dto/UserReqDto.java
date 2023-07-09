package com.sw.hearhere.web.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserReqDto {
    private UserReqDto() {
    }

    @Schema(title = "카카오 로그인 Req")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoUserInfo {
        @Schema(description = "카카오 유저id", example = "13432423")
        private Long id;

        @Schema(description = "유저 이메일", example = "peachpuchi@gmail.com")
        private String email;
    }
}
