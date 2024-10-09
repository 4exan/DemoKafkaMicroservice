package ua.kusakabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ua.kusakabe.entity.Follow;
import ua.kusakabe.entity.Post;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FollowRR {

    private int StatusCode;
    private String message;

    private String followerUsername;
    private String followedUsername;

    private List<String> followersList;
    private List<String> followedList;

    private List<Follow> follows;
    private List<Follow> followers;
    private List<UserWithPost> followedPosts;
    private List<Post> postList;

}
