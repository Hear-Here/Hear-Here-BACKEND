package com.sw.hearhere.web.post.dto;

import com.sw.hearhere.domain.entity.Music;
import com.sw.hearhere.domain.entity.Post;
import com.sw.hearhere.domain.entity.User;
import com.sw.hearhere.domain.enums.EmotionType;
import com.sw.hearhere.domain.enums.GenreType;
import com.sw.hearhere.domain.enums.WeatherType;
import com.sw.hearhere.domain.enums.WithType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @Schema(title = "게시물 상세조회 Res")
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class PostInfo{
        @Schema(description = "postId", example = "12")
        private Long postId;
        @Schema(description = "본인 게시물일 경우 true", example = "false")
        private Boolean isWriter;
        @Schema(description = "작성자 닉네임", example = "피치푸치")
        private String writer;
        @Schema(description = "노래명", example = "사람 냄새")
        private String title;
        @Schema(description = "가수", example = "정인, 개리")
        private String artist;
        @Schema(description = "앨범 커버 url", example = "https://cdnimg.melon.co.kr/cm/album/images/022/58/044/2258044_500.jpg/melon/resize/282/quality/80/optimize")
        private String cover;
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
        @Schema(description = "게시물까지 떨어진 거리(단위: m)", example = "13422")
        private Integer distance;
        @Schema(description = "좋아요 개수", example = "10,000")
        private Integer heartCount;
        @Schema(description = "좋아요 눌렀는지 여부", example = "false")
        private Boolean isHearted;

        public static PostInfo fromEntity(Post post, Integer distance, Boolean isHearted, Boolean isWriter){
            Music music = post.getMusic();
            User writer = post.getUser();
            int heartCount = post.getHeartList().size();
            return PostInfo.builder().postId(post.getId()).isWriter(isWriter).writer(writer.getNickname())
                    .title(music.getTitle()).artist(music.getArtist()).cover(music.getCover())
                    .genreType(post.getGenreType()).withType(post.getWithType()).temp(post.getTemp())
                    .weatherType(post.getWeatherType()).emotionType(post.getEmotionType()).content(post.getContent())
                    .longitude(post.getLongitude()).latitude(post.getLatitude()).distance(distance)
                    .heartCount(heartCount).isHearted(isHearted).build();
        }
    }

    @Schema(title = "게시물 삭제 Res")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeletePost {
        @Schema(description = "삭제된 게시물의 postId", example = "12")
        private Long postId;
    }

}