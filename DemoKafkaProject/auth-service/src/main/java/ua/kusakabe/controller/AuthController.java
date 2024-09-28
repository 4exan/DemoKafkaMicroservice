package ua.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.dto.AuthRR;
import ua.kusakabe.dto.ProfileDto;
import ua.kusakabe.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthRR> registerUser(@RequestBody AuthRR req){
        return ResponseEntity.ok(userService.saveUser(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthRR> loginUser(@RequestBody AuthRR req){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        if(authentication.isAuthenticated()){
            return ResponseEntity.ok(userService.loginUser(req));
        } else {
            throw new RuntimeException("Invalid username or password");
        }

    }

    @PostMapping("/validate")
    public String validateToken(@RequestBody AuthRR req){
        return userService.validateToken(req);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ProfileDto profile = userService.getMyProfile(username);
        return ResponseEntity.ok(profile);
    }

}
