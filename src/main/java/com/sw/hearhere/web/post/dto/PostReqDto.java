package com.sw.hearhere.web.post.dto;

import com.sw.hearhere.domain.enums.EmotionType;
import com.sw.hearhere.domain.enums.GenreType;
import com.sw.hearhere.domain.enums.WeatherType;
import com.sw.hearhere.domain.enums.WithType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostReqDto {

    private PostReqDto() {
    }

    @Schema(title = "게시물 업로드 Req")
    @NoArgsConstructor
    @Getter
    public static class UploadPost{
        @Schema(description = "노래명", example = "Hype Boy")
        private String title;
        @Schema(description = "가수", example = "New Jeans")
        private String artist;

        @Schema(description = "앨범 커버 url", example = "https://i1.sndcdn.com/artworks-AKBImWC0ywzWpI9z-ky9ZFw-t500x500.jpg")
        private String cover;

        @Schema(description = "Mania DB songId", example = "1012")
        private Long songId;

        @Schema(description = "노래 장르", example = "DANCE")
        private GenreType genreType;

        @Schema(description = "함께 듣는 사람", example = "ALONE")
        private WithType withType;

        @Schema(description = "날씨", example = "SUNNY")
        private WeatherType weatherType;

        @Schema(description = "기분", example = "SMILE")
        private EmotionType emotionType;

        @Schema(description = "온도", example = "29")
        private Integer temp;

        @Schema(description = "한줄 평", example = "요마 하아아압~")
        private String content;

        @Schema(description = "경도", example = "126.978427")
        private Double longitude;

        @Schema(description = "위도", example = "37.566667")
        private Double latitude;
    }
}
