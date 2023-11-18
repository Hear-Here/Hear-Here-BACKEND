package com.sw.hearhere.web.user.dto;

import com.sw.hearhere.domain.enums.AuthState;
import com.sw.hearhere.jwt.TokensDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResDto {
    private UserResDto() {
    }
    @Schema(title = "카카오 로그인 Res")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoLogin {

        @Schema(description = "회원가입/로그인 상태", example = "LOGIN")
        private AuthState authState;

        @Schema(description = "accessToken", example = "eyJhbGciOiJIUzUxMiJ9" +
                ".eyJzdWIiOiJ7XCJpZFwiOjEsXCJlbWFpbFwiOlwiZW1haWxAZW1haWwuY29tXCJ9IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY3OTIyNzc0NX0.gM7F00Dh7OvybtEYODxYqFNATDDdquGCIivAeifNrEnF1ush3Fx1ChWqwD60U6Ek7rmJRU3CUUFAMLUrWDi4Aw")
        private String accessToken;

        @Schema(description = "refreshToken", example = "eyJhbGciOiJIUzUxMiJ9" +
                ".eyJzdWIiOiJ7XCJpZFwiOjEsXCJlbWFpbFwiOlwiZW1haWxAZW1haWwuY29tXCJ9IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY3OTgzMDc0NX0.OBtrGGKuSujBxwLTNVs-sc4eEH8uYiG8-Cwomqb_OgB9ADVbWbtSqaml9Ll34TFrKhPZuhMvzdchsWHqMQQ_kg")
        private String refreshToken;

        public static KakaoLogin fromTokens(AuthState authState, TokensDto tokensDto) {
            return new KakaoLogin(authState, tokensDto.getAccessToken(), tokensDto.getRefreshToken());
        }
    }
}