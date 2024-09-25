package ua.kusakabe.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.PostEntity;
import ua.kusakabe.payload.Post;
import ua.kusakabe.repository.PostRepository;

@Service
public class PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    public void save(Post payload){
        PostEntity toSavePost = new PostEntity();
        try{
            toSavePost.setUsername(payload.getUsername());
            toSavePost.setTitle(payload.getTitle());
            toSavePost.setContent(payload.getContent());
            toSavePost.setTimestamp(payload.getTimestamp());
            PostEntity result = postRepository.save(toSavePost);
            if(result.getId() > 0){
                LOGGER.info("Post: {} successfully saved!", result.getId());
            } else {
                LOGGER.error("Post: {} failed to saved!", result.getId());
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }

}
