package ua.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.kusakabe.dto.AuthRR;
import ua.kusakabe.dto.ProfileDto;
import ua.kusakabe.entity.User;
import ua.kusakabe.repository.UserRepository;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.restTemplate = restTemplate;
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

    public ProfileDto getMyProfile(String username){
        ProfileDto res = new ProfileDto();
        try{
            String resBody = restTemplate.getForObject("http://localhost:8765/user/image/" + username, String.class);
            if(resBody != null) {
                LOGGER.info("Response body: " + resBody);
            } else {
                LOGGER.info("Response body is null");
            }
            Optional<User> user = userRepository.findByUsername(username);
            if(user.isPresent()){
                res.setUsername(user.get().getUsername());
                res.setFirstName(user.get().getFirstName());
                res.setLastName(user.get().getLastName());
                res.setEmail(user.get().getEmail());
                res.setPhone(user.get().getPhone());
            } else {
                throw new RuntimeException("No such user");
            }
        }catch (Exception e){
            throw new RuntimeException("Error while getting user profile");
        }
        return res;
    }

}
