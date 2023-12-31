package com.sw.hearhere.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokensDto {

    private String accessToken;

    private String refreshToken;

    @Builder
    public TokensDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
