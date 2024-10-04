package ua.kusakabe.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;

@Entity
@Table(name = "likes")
@IdClass(LikeId.class)
public class Like implements Serializable {

    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
//    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;
    @ManyToOne
//    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Post post;

    public Like() {
    }

    public Like(String username, Long postId, User user, Post post) {
        this.username = username;
        this.postId = postId;
        this.user = user;
        this.post = post;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
