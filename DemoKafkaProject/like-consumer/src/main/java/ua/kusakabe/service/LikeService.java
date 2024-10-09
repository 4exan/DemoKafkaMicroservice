package ua.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.LikeEntity;
import ua.kusakabe.payload.Like;
import ua.kusakabe.repository.LikeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(LikeService.class);

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public void save(Like payload) {
        LikeEntity like = new LikeEntity();
        try{
            like.setUsername(payload.getUsername());
            like.setPostId(payload.getPost_id());
            LikeEntity result = likeRepository.save(like);
            if(result.getUsername().equals(payload.getUsername())) {
                LOGGER.info("Successfully saved like " + payload.getUsername());
            } else {
                LOGGER.error("Failed to save like " + payload.getUsername());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to save like " + payload.getUsername());
        }
    }
}
