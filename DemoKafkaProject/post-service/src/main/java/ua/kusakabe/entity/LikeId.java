package ua.kusakabe.entity;

import java.io.Serializable;
import java.util.Objects;

public class LikeId implements Serializable {

    private String username;
    private Long postId;

    public LikeId() {
    }

    public LikeId(String username, Long post) {
        this.username = username;
        this.postId = post;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPost() {
        return postId;
    }

    public void setPost(Long post) {
        this.postId = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeId likeId = (LikeId) o;
        return Objects.equals(username, likeId.username) && Objects.equals(postId, likeId.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, postId);
    }
}
