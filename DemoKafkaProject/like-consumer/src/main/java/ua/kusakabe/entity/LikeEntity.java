package ua.kusakabe.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "likes")
@IdClass(LikeId.class)
public class LikeEntity implements Serializable {

    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "post_id")
    private Long postId;

//    @ManyToOne
////    @Fetch(FetchMode.JOIN)
//    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
//    private User user;
//    @ManyToOne
////    @Fetch(FetchMode.JOIN)
//    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private Post post;

    public LikeEntity() {
    }

    public LikeEntity(String username, Long postId) {
        this.username = username;
        this.postId = postId;
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
}
