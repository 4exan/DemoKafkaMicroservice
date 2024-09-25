package ua.kusakabe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kusakabe.dto.AuthRR;
import ua.kusakabe.entity.User;
import ua.kusakabe.repository.UserRepository;

import java.util.HashMap;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthRR saveUser(AuthRR req) {
        AuthRR res = new AuthRR();
        try{
            User toSave = new User();
            toSave.setUsername(req.getUsername());
            toSave.setPassword(passwordEncoder.encode(req.getPassword()));
            toSave.setFirstName(req.getFirstName());
            toSave.setLastName(req.getLastName());
            toSave.setEmail(req.getEmail());
            toSave.setPhone(req.getPhone());
            toSave.setRole(req.getRole());
            User result = userRepository.save(toSave);
            if(result.getId() > 0) {
                res.setStatusCode(200);
                res.setMessage("User has been saved!");
                res.setUser(result);
            } else {
                res.setStatusCode(400);
                res.setMessage("User save failed!");
            }
        }catch (Exception e){
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public AuthRR loginUser(AuthRR req) {
        AuthRR res = new AuthRR();
        try{
            String token = jwtService.generateToken(req);
            String refreshedToken = jwtService.generateRefreshToken(new HashMap<>(), req);
            if(token != null) {
                res.setStatusCode(200);
                res.setMessage("User logged in successfully!");
                res.setToken(token);
                res.setRefreshToken(refreshedToken);
            } else {
                res.setStatusCode(400);
                res.setMessage("User login failed!");
            }
        }catch (Exception e){
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public String validateToken(AuthRR req) {
        String response;
        try{
            User tokenBearer = userRepository.findByUsername(jwtService.extractUsername(req.getToken())).orElseThrow(()->new RuntimeException("No such user in database!"));
            boolean isValid = jwtService.validateToken(req.getToken(), tokenBearer.getUsername());
            if(isValid){
                response = "200";
            } else {
                response = "400";
            }
        }catch (Exception e){
            response = "500";
        }
        return response;
    }

}
