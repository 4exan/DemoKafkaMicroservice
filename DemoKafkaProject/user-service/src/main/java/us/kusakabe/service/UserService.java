package us.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.kusakabe.dto.FollowRR;
import us.kusakabe.dto.UserProfile;
import us.kusakabe.entity.Follow;
import us.kusakabe.entity.ProfilePicture;
import us.kusakabe.entity.User;
import us.kusakabe.repository.FollowRepository;
import us.kusakabe.repository.ProfilePictureRepository;
import us.kusakabe.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfilePictureRepository profilePictureRepository;
    private final JwtService jwtService;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final FollowRepository followRepository;

    @Autowired
    public UserService(UserRepository userRepository, ProfilePictureRepository profilePictureRepository, JwtService jwtService, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.profilePictureRepository = profilePictureRepository;
        this.jwtService = jwtService;
        this.followRepository = followRepository;
    }

    public UserProfile getUserProfile(String username) {
        UserProfile userProfile = new UserProfile();
        try{
            User user = userRepository.findByUsername(username);
            ProfilePicture img = profilePictureRepository.findByUsername(username).orElse(null);
            userProfile.setUsername(user.getUsername());
            userProfile.setFirstName(user.getFirstName());
            userProfile.setLastName(user.getLastName());
            userProfile.setEmail(user.getEmail());
            userProfile.setPhone(user.getPhone());
            userProfile.setProfilePicture(img);
            if(!userProfile.getUsername().isBlank()){
                return userProfile;
            } else {
                LOGGER.warn("Username is blank!");
            }
        } catch (Exception e){
            LOGGER.error("Error while getting user profile -> ", e);
        }
        return userProfile;
    }

    public void followUser(String username, String header) {
        try{
            String follower = jwtService.extractUsername(header.substring(7));
            if(!follower.isBlank()){
                Follow follow = new Follow();
                follow.setFollower(follower);
                follow.setFollowed(username);
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
            if(followRepository.findByFollowerAndFollowed(follower, username).isPresent()){
                return true;
            }
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
//                res.setFollowers(dbList);
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
