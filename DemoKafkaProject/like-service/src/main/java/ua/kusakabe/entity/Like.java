package ua.kusakabe.entity;

import jakarta.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Post post;

    public Like() {
    }

    public Like(String username, Long postId, Post post) {
        this.username = username;
        this.postId = postId;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
