package us.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.kusakabe.dto.UserProfile;
import us.kusakabe.entity.Follow;
import us.kusakabe.entity.User;
import us.kusakabe.repository.FollowRepository;
import us.kusakabe.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final FollowRepository followRepository;
    private final ImageService imageService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService, FollowRepository followRepository, ImageService imageService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.followRepository = followRepository;
        this.imageService = imageService;
    }

    public UserProfile getMyProfile(String header) {
        String username = jwtService.extractUsername(header.substring(7));
        try{
            Optional<User> user = userRepository.findByUsername(username);
            if(user.isPresent()) {
                byte[] img = imageService.downloadImage(username);
                 UserProfile userProfile = new UserProfile.UserProfileBuilder(
                        user.get().getUsername(),
                        user.get().getFirstName(),
                        user.get().getLastName(),
                        user.get().getEmail(),
                        user.get().getPhone()
                ).profilePictureBytes(img).build();
                LOGGER.info("User: {} loaded successfully!", username);
                return userProfile;
            } else {
                LOGGER.warn("User: {} not found!", username);
                throw new RuntimeException("User not found!");
            }
        } catch (Exception e){
            LOGGER.error("Error while getting user profile -> ", e);
        }
        throw new RuntimeException("User not found!");
    }

    public UserProfile getUserProfile(String username, String header) {
        String follower = jwtService.extractUsername(header.substring(7));
        try{
            Optional<User> user = userRepository.findByUsername(username);
            Optional<Follow> follow = followRepository.findByFollowerAndFollowed(follower, username);
            if(user.isPresent()) {
                byte[] img = imageService.downloadImage(username);
                UserProfile userProfile = new UserProfile.UserProfileBuilder(
                        user.get().getUsername(),
                        user.get().getFirstName(),
                        user.get().getLastName(),
                        user.get().getEmail(),
                        user.get().getPhone())
                        .profilePictureBytes(img)
                        .isFollowed(follow.isPresent())
                        .build();
                LOGGER.info("User: {} loaded successfully!", username);
                return userProfile;
            } else {
                LOGGER.warn("User: {} not found!", username);
            }
        } catch (Exception e){
            LOGGER.error("Error while getting user profile -> ", e);
        }
        throw new RuntimeException("User not found!");
    }

    public void editUserProfile(UserProfile req, String header) {
        String username = jwtService.extractUsername(header.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found!"));
        try{
            user.setFirstName(req.getFirstName());
            user.setLastName(req.getLastName());
            user.setEmail(req.getEmail());
            user.setPhone(req.getPhone());
            User savedUser = userRepository.save(user);
            if(savedUser.getUsername().equals(username)) {
                LOGGER.info("User: {} successfully edited!", username);
            } else {
                LOGGER.warn("User: {} not found!", username);
            }
        } catch (Exception e) {
            LOGGER.error("Error while editing user profile -> ", e);
        }
    }
}
