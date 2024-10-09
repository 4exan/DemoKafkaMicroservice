package ua.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.dto.LikeRR;
import ua.kusakabe.service.LikeService;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/{postId}")
    public void likePost(@RequestHeader("Authorization") String token, @PathVariable("postId") int postId){
        likeService.likePost(token, postId);
    }

    @GetMapping("/remove/{postId}")
    public void dislikePost(@RequestHeader("Authorization") String token, @PathVariable("postId") int postId){
        likeService.dislikePost(token, postId);
    }

    @GetMapping("/get")
    public ResponseEntity<LikeRR> getAllLikes(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(likeService.getAllUserLikes(token));
    }

}
