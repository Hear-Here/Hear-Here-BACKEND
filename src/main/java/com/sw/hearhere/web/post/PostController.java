package com.sw.hearhere.web.post;

import com.sw.hearhere.response.BaseException;
import com.sw.hearhere.response.BaseResponse;
import com.sw.hearhere.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.sw.hearhere.web.post.dto.PostReqDto.*;
import static com.sw.hearhere.web.post.dto.PostResDto.*;


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
    public ResponseEntity<CreatePost> uploadPost(@RequestBody UploadPost uploadPost) {
        Long newPostId = postService.createPost(uploadPost);
        return ResponseEntity.ok(new CreatePost(newPostId));
    }

    /**
     * 게시물 조회
     */
    @Operation(summary = "게시물 상세조회", description = "게시물 상세조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostInfo> findPost(@Parameter(description = "postId") @PathVariable(value = "postId") Long postId,
                                             @Parameter(description = "위도") @RequestParam(value = "latitude") Double latitude,
                                             @Parameter(description = "경도") @RequestParam(value = "longitude") Double longitude) {
        PostInfo postInfo = postService.findPostById(postId, latitude, longitude);
        return ResponseEntity.ok().body(postInfo);
    }

    /**
     * 게시물 삭제
     */
    @Operation(summary = "게시물 삭제", description = "게시물 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<DeletePost> deletePost(@PathVariable Long postId) {
        Long deletedPostId = postService.deletePost(postId);
        return ResponseEntity.ok(new DeletePost(deletedPostId));
    }

    /**
     * 게시물 리스트(필터링: 장르, 듣는사람, 날씨, 감정)
     */
    @Operation(summary = "게시물 리스트 조회 - 지도", description = "게시물 리스트 조회(필터링: 장르, 듣는사람, 날씨, 감정)")
    @GetMapping("/list-map")
    public ResponseEntity<List<PostInfo>> postListForMap(
            @Parameter(description = "위도") @RequestParam(value = "latitude", required = false) Double latitude
            , @Parameter(description = "경도") @RequestParam(value = "longitude", required = false) Double longitude
            , @RequestParam(required = false) Map<String, String> filterMap) {
        List<PostInfo> postInfoList = postService.postListForMap(latitude, longitude, filterMap);
        return ResponseEntity.ok(postInfoList);
    }

    @Operation(summary = "게시물 한줄평 수정", description = "게시물 한줄평 수정")
    @PatchMapping("/{postId}")
    public ResponseEntity<String> updatePostContent(@RequestBody UpdatePostContent updateContent,
                                                    @Parameter(description = "게시물Id") @PathVariable(value = "postId") Long postId) {
        postService.updatePostContent(postId, updateContent.getContent());
        return ResponseEntity.ok().body("Success");
    }

    @Operation(summary = "내가 쓴 게시물 리스트 조회", description = "내가 쓴 게시물 리스트 조회")
    @GetMapping("/list/my")
    public ResponseEntity<List<PostInfo>> myPost(@RequestParam Double latitude,
                                                 @RequestParam Double longitude){
        List<PostInfo> postList = postService.myPostList(latitude, longitude);
        return ResponseEntity.ok().body(postList);
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse<String> handleBaseException(BaseException e) {
        log.info(e.getStatus().toString());
        return new BaseResponse<>(e.getStatus());
    }

}
