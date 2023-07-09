package com.sw.hearhere.web.post;

import com.sw.hearhere.response.BaseException;
import com.sw.hearhere.response.BaseResponse;
import com.sw.hearhere.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sw.hearhere.web.post.dto.PostResDto.*;
import static com.sw.hearhere.web.post.dto.PostReqDto.*;


@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "Post", description = "게시물 API")
public class PostController {

    private final PostService postService;

    /**
     * 게시물 작성
     */
    @Operation(summary = "게시물 작성", description = "게시물 작성")
    @PostMapping("")
    public ResponseEntity<CreatePost> uploadPost(@RequestBody UploadPost uploadPost){
        Long newPostId = postService.createPost(uploadPost);
        return ResponseEntity.ok(new CreatePost(newPostId));
    }

    /**
     * 게시물 조회
     */
    @Operation(summary = "게시물 상세조회", description = "게시물 상세조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostInfo> findPost(@PathVariable Long postId, @RequestParam Double latitude,
                                             @RequestParam Double longitude){
        PostInfo postInfo = postService.findPostById(postId, latitude, longitude);
        return ResponseEntity.ok().body(postInfo);
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse<String> handleBaseException(BaseException e) {
        log.info(e.getStatus().toString());
        return new BaseResponse<>(e.getStatus());
    }

}
