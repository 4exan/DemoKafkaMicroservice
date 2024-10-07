package us.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.kusakabe.dto.FollowRR;
import us.kusakabe.dto.UserProfile;
import us.kusakabe.entity.Follow;
import us.kusakabe.entity.User;
import us.kusakabe.repository.FollowRepository;
import us.kusakabe.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
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

    public void followUser(String followed, String header) {
        try{
            String follower = jwtService.extractUsername(header.substring(7));
            if(!follower.isBlank()){
                Follow follow = new Follow(follower, followed);
                followRepository.save(follow);
                LOGGER.info("Followed user : {}", follower);
            } else {
                LOGGER.warn("Username is blank!");
            }
        } catch (Exception e){
            LOGGER.error("Error while following user -> ", e);
        }
    }

    public void unfollowUser(String username, String header) {
        try{
            String follower = jwtService.extractUsername(header.substring(7));
            if(followRepository.findByFollowerAndFollowed(follower, username).isPresent()){
                followRepository.delete(followRepository.findByFollowerAndFollowed(follower, username).get());
                LOGGER.info("Unfollowed user : {}", username);
            } else {
                LOGGER.warn("Username is blank!");
            }
        }catch (Exception e){
            LOGGER.error("Error while unfollowing user -> ", e);
        }
    }

    public boolean isUserFollowed(String header, String username) {
        try{
            String follower = jwtService.extractUsername(header.substring(7));
            return followRepository.findByFollowerAndFollowed(follower, username).isPresent();
        } catch (Exception e){
            LOGGER.error("Error while checking user follow has occurred -> ", e);
        }
        return false;
    }

    public FollowRR getUserFollows(String username) {
        FollowRR res = new FollowRR();
        try{
            List<Follow> dbList = followRepository.findAllByFollower(username);
            if(!dbList.isEmpty()){
                res.setStatusCode(200);
                res.setMessage("User follows loaded successfully!");
                List<String> followedList = new ArrayList<>();
                for(Follow follower : dbList){
                    followedList.add(follower.getFollowed());
                }
                res.setFollowedList(followedList);
            } else {
                res.setStatusCode(404);
                res.setMessage("User followers not found!");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public FollowRR getUserFollowers(String username) {
        FollowRR res = new FollowRR();
        try{
            List<Follow> dbList = followRepository.findAllByFollowed(username);
            if(!dbList.isEmpty()){
                res.setStatusCode(200);
                res.setMessage("User followers loaded successfully!");
                List<String> followerList = new ArrayList<>();
                for(Follow follower : dbList){
                    followerList.add(follower.getFollower());
                }
                res.setFollowedList(followerList);
            } else {
                res.setStatusCode(404);
                res.setMessage("User follow list is empty!");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
