package com.sw.hearhere.service;

import com.sw.hearhere.domain.entity.Music;
import com.sw.hearhere.domain.entity.Post;
import com.sw.hearhere.domain.entity.User;
import com.sw.hearhere.domain.repository.HeartRepository;
import com.sw.hearhere.domain.repository.MusicRepository;
import com.sw.hearhere.domain.repository.PostRepository;
import com.sw.hearhere.domain.repository.UserRepository;
import com.sw.hearhere.response.BaseException;
import com.sw.hearhere.utils.SecurityUtil;
import com.sw.hearhere.web.post.dto.PostReqDto;
import com.sw.hearhere.web.post.dto.PostResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sw.hearhere.response.BaseResponseStatus.NOT_FOUND_POST;
import static com.sw.hearhere.response.BaseResponseStatus.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final UserRepository userRepository;
    private final MusicRepository musicRepository;
    private final PostRepository postRepository;
    private final HeartRepository heartRepository;

    private static final int EARTH_RADIUS = 6371; //km

    public Long createPost(PostReqDto.UploadPost uploadPost) {
        User user = userRepository.findById(SecurityUtil.getLoginUserId())
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));

        //Music 테이블에 없으면 추가
        if (!musicRepository.existsByTitleAndArtist(uploadPost.getTitle(), uploadPost.getArtist())) {
            Music music = Music.builder().title(uploadPost.getTitle()).artist(uploadPost.getArtist())
                    .cover(uploadPost.getCover()).songId(uploadPost.getSongId()).build();
            musicRepository.save(music);
        }
        Music music = musicRepository.findByTitleAndArtist(uploadPost.getTitle(), uploadPost.getArtist())
                .orElseThrow(() -> new IllegalArgumentException("not found music"));

        Post post = Post.builder().user(user).content(uploadPost.getContent()).emotionType(uploadPost.getEmotionType())
                .genreType(uploadPost.getGenreType()).withType(uploadPost.getWithType())
                .weatherType(uploadPost.getWeatherType()).latitude(uploadPost.getLatitude())
                .longitude(uploadPost.getLongitude()).music(music).temp(uploadPost.getTemp())
                .build();
        return postRepository.save(post).getId();
    }

    public PostResDto.PostInfo findPostById(Long postId, Double latitude, Double longitude) {
        User user = userRepository.findById(SecurityUtil.getLoginUserId())
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        Post post = postRepository.findById(postId).orElseThrow(()-> new BaseException(NOT_FOUND_POST));
        int distance = calculateDistance(latitude, longitude, post.getLatitude(), post.getLongitude());
        boolean isHearted = heartRepository.existsByPostAndUser(post, user);
        boolean isWriter = false;
        if(post.getUser()==user){
            isWriter = true;
        }
        return PostResDto.PostInfo.fromEntity(post, distance, isHearted, isWriter);
    }

    private int calculateDistance(double userLat, double userLon, double postLat, double postLon) {
        double dLat = Math.toRadians(postLat - userLat);
        double dLon = Math.toRadians(postLon - userLon);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(userLat))* Math.cos(Math.toRadians(postLat))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (int)Math.round(EARTH_RADIUS* c * 1000); // Distance in m
    }
}
