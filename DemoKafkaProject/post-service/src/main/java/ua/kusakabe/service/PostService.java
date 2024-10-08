package ua.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kusakabe.dto.PostRR;
import ua.kusakabe.entity.Post;
import ua.kusakabe.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final JwtService jwtService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public PostService(PostRepository postRepository, JwtService jwtService) {
        this.postRepository = postRepository;
        this.jwtService = jwtService;
    }

    public PostRR getPostById(PostRR req) {
        PostRR res = new PostRR();
        try{
            Post post = postRepository.getReferenceById(req.getId());
            if(post.getId() > 0){
                res.setPost(post);
                res.setStatusCode(200);
                LOGGER.info("Loaded post with id: {}", post.getId());
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
            LOGGER.error("Error while getting post by id -> {}", e.getMessage());
        }
        return res;
    }

    public PostRR getAllUserPosts(String header) {
        // HANDLE EXCEPTION !!!
        String token = header.substring(7);
        String username = jwtService.extractUsername(token);
        PostRR res = new PostRR();
        try{
            List<Post> posts = postRepository.findAllByUsername(username);
            if(!posts.isEmpty()){
                res.setPostList(posts);
                res.setStatusCode(200);
                res.setMessage("User posts loaded successfully!");
                LOGGER.info("Loaded all users post for {}", username);
            }
        }catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
            LOGGER.error("Error while getting all user posts -> {}", e.getMessage());
        }
        return res;
    }

    public PostRR getAllPostsByUsername(String username){
        PostRR res = new PostRR();
        try{
            List<Post> posts = postRepository.findAllByUsername(username);
            if(!posts.isEmpty()){
                res.setPostList(posts);
                res.setStatusCode(200);
                res.setMessage("Successfully loaded posts!");
                LOGGER.info("Loaded all users post for {}", username);
            } else {
                res.setStatusCode(404);
                res.setMessage("No posts were found!");
            }
        }catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
            LOGGER.error("Error while getting all user {} posts -> {}", username, e.getMessage());
        }
        return res;
    }

    public void removePost(long postId) {
        postRepository.deleteById(postId);
        LOGGER.info("Deleted post with id {}", postId);
    }

}
