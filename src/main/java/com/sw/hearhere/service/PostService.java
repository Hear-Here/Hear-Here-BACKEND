package com.sw.hearhere.service;

import com.sw.hearhere.domain.entity.Music;
import com.sw.hearhere.domain.entity.Post;
import com.sw.hearhere.domain.entity.User;
import com.sw.hearhere.domain.repository.MusicRepository;
import com.sw.hearhere.domain.repository.PostRepository;
import com.sw.hearhere.domain.repository.UserRepository;
import com.sw.hearhere.response.BaseException;
import com.sw.hearhere.utils.SecurityUtil;
import com.sw.hearhere.web.post.dto.PostReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sw.hearhere.response.BaseResponseStatus.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final UserRepository userRepository;
    private final MusicRepository musicRepository;
    private final PostRepository postRepository;

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
}
