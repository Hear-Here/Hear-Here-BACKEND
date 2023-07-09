package com.sw.hearhere.web.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostResDto {
    private PostResDto() {
    }

    @Schema(title = "게시물 작성 Res")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePost {
        @Schema(description = "업로드된 게시물의 postId", example = "12")
        private Long postId;
    }
}