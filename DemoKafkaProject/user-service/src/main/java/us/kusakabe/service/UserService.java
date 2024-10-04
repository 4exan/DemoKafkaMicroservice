package us.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.kusakabe.dto.UserProfile;
import us.kusakabe.entity.User;
import us.kusakabe.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfile getUserProfile(String username) {
        UserProfile userProfile = new UserProfile();
        try{
            User user = userRepository.findByUsername(username);
            userProfile.setUsername(user.getUsername());
            userProfile.setFirstName(user.getFirstName());
            userProfile.setLastName(user.getLastName());
            userProfile.setEmail(user.getEmail());
            userProfile.setPhone(user.getPhone());
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
