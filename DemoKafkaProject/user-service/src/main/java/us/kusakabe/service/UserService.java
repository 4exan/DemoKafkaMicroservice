package us.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.kusakabe.dto.UserProfile;
import us.kusakabe.entity.ProfilePicture;
import us.kusakabe.entity.User;
import us.kusakabe.repository.ProfilePictureRepository;
import us.kusakabe.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfilePictureRepository profilePictureRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, ProfilePictureRepository profilePictureRepository) {
        this.userRepository = userRepository;
        this.profilePictureRepository = profilePictureRepository;
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

}
