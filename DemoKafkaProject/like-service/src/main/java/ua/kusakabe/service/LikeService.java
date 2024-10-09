package ua.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kusakabe.dto.LikeRR;
import ua.kusakabe.entity.Like;
import ua.kusakabe.entity.Post;
import ua.kusakabe.repository.LikeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final JwtService jwtService;
    private static final Logger LOGGER = LoggerFactory.getLogger(LikeService.class);

    @Autowired
    public LikeService(LikeRepository likeRepository, JwtService jwtService) {
        this.likeRepository = likeRepository;
        this.jwtService = jwtService;
    }

    public void likePost(String header, int postId){
        String token = header.substring(7);
        String username = jwtService.extractUsername(token);
        try{
            Like isLiked = likeRepository.findByUsernameAndPostId(username, postId).orElse(null);
            if(isLiked == null){
                Like like = new Like();
                like.setUsername(username);
                like.setPostId((long)postId);
                likeRepository.save(like);
                LOGGER.info("Liked post with id: {}", postId);
            } else {
                throw new RuntimeException("Post already liked!");
            }
        }catch (Exception e){
            LOGGER.error("Error while getting like -> {}", e.getMessage());
        }
    }

    public void dislikePost(String header, int postId){
        String username = jwtService.extractUsername(header.substring(7));
        try{
            Like isLiked = likeRepository.findByUsernameAndPostId(username, postId).orElse(null);
            if(isLiked != null) {
                likeRepository.delete(isLiked);
            } else {
                LOGGER.warn("Can't dislike post with id: {}", postId);
            }
        } catch (Exception e) {
            LOGGER.error("Error while getting dislike -> {}", e.getMessage());
        }
    }

    public LikeRR getAllUserLikes(String header){
        LikeRR res = new LikeRR();
        List<Post> posts = new ArrayList<>();
        String username = jwtService.extractUsername(header.substring(7));
        try{
            List<Like> likes = likeRepository.findAllByUsername(username);
            if(!likes.isEmpty()){
                for(Like like : likes){
                    posts.add(like.getPost());
                }
                res.setPostList(posts);
                res.setStatusCode(200);
                res.setMessage("Liked posts loaded!");
                LOGGER.info("Loaded all users like for {}", username);
            }
        } catch (Exception e) {
            LOGGER.error("Error while getting all user likes -> {}", e.getMessage());
        }
        return res;
    }

}
