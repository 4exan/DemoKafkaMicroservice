package ua.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.FollowEntity;
import ua.kusakabe.payload.Follow;
import ua.kusakabe.repository.FollowRepository;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(FollowService.class);

    @Autowired
    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public void save(Follow payload) {
        FollowEntity followEntity = new FollowEntity();
        try{
            if(!payload.getFollower().equals(payload.getFollowed())) {
                followEntity.setFollower(payload.getFollower());
                followEntity.setFollowed(payload.getFollowed());
                followRepository.save(followEntity);
                LOGGER.info("Follow");
            } else {
                LOGGER.info("Follow already exists!");
            }
        } catch (Exception e){
            LOGGER.error("Error while saving follow {} -> {}", payload.getFollower(), payload.getFollowed(), e);
        }
    }

}
