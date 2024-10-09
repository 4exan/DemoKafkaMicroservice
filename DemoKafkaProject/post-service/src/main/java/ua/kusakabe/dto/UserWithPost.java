package ua.kusakabe.dto;

import java.util.List;

public class UserWithPost {

    private List<UserRR> userWithPosts;

    public UserWithPost() {
    }

    public UserWithPost(List<UserRR> userWithPosts) {
        this.userWithPosts = userWithPosts;
    }

    public List<UserRR> getUserWithPosts() {
        return userWithPosts;
    }

    public void setUserWithPosts(List<UserRR> userWithPosts) {
        this.userWithPosts = userWithPosts;
    }
}
