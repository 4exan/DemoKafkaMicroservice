package us.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.kusakabe.dto.UserProfile;
import us.kusakabe.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getMyProfile(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserProfile(username));
    }

}
