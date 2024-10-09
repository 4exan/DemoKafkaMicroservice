package ua.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.dto.FollowRR;
import ua.kusakabe.dto.PostRR;
import ua.kusakabe.dto.UserWithPost;
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

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable long id){
        postService.removePost(id);
    }

    @PostMapping("/followed")
    public ResponseEntity<UserWithPost> getFollowedUsersPosts(@RequestBody FollowRR req){
        return ResponseEntity.ok(postService.getPostsByFollowedList(req));
    }

}
