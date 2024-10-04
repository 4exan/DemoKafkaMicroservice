package ua.kusakabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ua.kusakabe.entity.Like;
import ua.kusakabe.entity.Post;
import ua.kusakabe.entity.User;

import java.util.List;

@RequiredArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LikeRR {

    private int statusCode;
    private String message;

    private String username;
    private long postId;

    private User user;
    private Post post;

    private List<User> userList;
    private List<Post> postList;
    private List<Like> likeList;

}
