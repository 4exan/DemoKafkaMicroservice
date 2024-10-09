package ua.kusakabe.dto;

import ua.kusakabe.entity.Post;

import java.util.List;

public class UserRR {

    private String username;
    private List<Post> postList;

    public UserRR() {
    }

    public UserRR(String username, List<Post> postList) {
        this.username = username;
        this.postList = postList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
}
