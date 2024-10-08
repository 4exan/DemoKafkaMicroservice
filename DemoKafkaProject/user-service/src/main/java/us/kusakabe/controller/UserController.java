package us.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PutMapping("/edit")
    public void editUser(@RequestBody UserProfile req, @RequestHeader("Authorization") String header) {
        userService.editUserProfile(req, header);
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
}
