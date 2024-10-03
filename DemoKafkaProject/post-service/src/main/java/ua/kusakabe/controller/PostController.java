package ua.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.dto.LikeRR;
import ua.kusakabe.dto.PostRR;
import ua.kusakabe.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/get")
    public ResponseEntity<PostRR> getUserPosts(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(postService.getAllUserPosts(token));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<PostRR> getPostByUsername(@PathVariable String username){
        return ResponseEntity.ok(postService.getAllPostsByUsername(username));
    }

    @PostMapping("/like/{postId}")
    public void likePost(@RequestHeader("Authorization") String token, @PathVariable("postId") int postId){
        postService.likePost(token, postId);
    }

    @PostMapping("/dislike/{postId}")
    public void dislikePost(@RequestHeader("Authorization") String token, @PathVariable("postId") int postId){
        postService.dislikePost(token, postId);
    }

    @GetMapping("/like/get")
    public ResponseEntity<LikeRR> getAllLikes(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(postService.getAllUserLikes(token));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable long id){
        postService.removePost(id);
    }

}
