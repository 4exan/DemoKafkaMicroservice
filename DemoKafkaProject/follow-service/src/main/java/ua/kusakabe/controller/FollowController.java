package ua.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.dto.FollowRR;
import ua.kusakabe.service.FollowService;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/remove/{username}")
    public ResponseEntity<?> unfollowUser(@RequestHeader("Authorization") String token, @PathVariable("username") String username){
        followService.unfollowUser(username, token);
        return ResponseEntity.ok("User '" + username + "' successfully unfollowed!");
    }

    @GetMapping("/followed/{username}")
    public ResponseEntity<?> isUserFollowed(@RequestHeader("Authorization") String token,@PathVariable("username") String username){
        return ResponseEntity.ok(followService.isUserFollowed(token, username));
    }

    @GetMapping("/get-followers/{username}")
    public ResponseEntity<FollowRR> getFollowers(@PathVariable("username") String username){
        return ResponseEntity.ok(followService.getUserFollowers(username));
    }

    @GetMapping("/get-follows/{username}")
    public ResponseEntity<FollowRR> getFollowed(@PathVariable("username") String username){
        return ResponseEntity.ok(followService.getUserFollows(username));
    }

    @GetMapping("/get-followed-posts")
    public ResponseEntity<FollowRR> testRequestToPostService(@RequestHeader("Authorization") String header){
        return ResponseEntity.ok(followService.testRequestToPostService(header));
    }

}
