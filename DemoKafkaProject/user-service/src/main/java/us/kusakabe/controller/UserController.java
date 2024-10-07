package us.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import us.kusakabe.dto.FollowRR;
import us.kusakabe.dto.UserProfile;
import us.kusakabe.service.ImageService;
import us.kusakabe.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getMyProfile(@RequestHeader("Authorization") String header) {
        return ResponseEntity.ok(userService.getMyProfile(header));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable String username, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok(userService.getUserProfile(username, header));
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("image") MultipartFile image, @RequestHeader("Authorization") String header) throws IOException {
        imageService.uploadImage(image, header);
        return ResponseEntity.ok("Image uploaded");
    }

    @GetMapping("/image/{username}")
    public ResponseEntity<?> getImage(@PathVariable String username) {
        byte[] imageData = imageService.downloadImage(username);
        return ResponseEntity.ok().contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE)).body(imageData);
    }

    @GetMapping("/follow/{username}")
    public ResponseEntity<?> followUser(@RequestHeader("Authorization") String token,@PathVariable("username") String username){
        userService.followUser(username, token);
        return ResponseEntity.ok("User '" + username + "' successfully followed!");
    }

    @GetMapping("/unfollow/{username}")
    public ResponseEntity<?> unfollowUser(@RequestHeader("Authorization") String token,@PathVariable("username") String username){
        userService.unfollowUser(username, token);
        return ResponseEntity.ok("User '" + username + "' successfully unfollowed!");
    }

    @GetMapping("/followed/{username}")
    public ResponseEntity<?> isUserFollowed(@RequestHeader("Authorization") String token,@PathVariable("username") String username){
        return ResponseEntity.ok(userService.isUserFollowed(token, username));
    }

    @GetMapping("/get-followers/{username}")
    public ResponseEntity<FollowRR> getFollowers(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.getUserFollowers(username));
    }

    @GetMapping("/get-follows/{username}")
    public ResponseEntity<FollowRR> getFollowed(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.getUserFollows(username));
    }

}
