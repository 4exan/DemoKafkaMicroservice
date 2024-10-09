package ua.kusakabe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "follows")
@IdClass(FollowId.class)
public class FollowEntity {

    @Id
    @Column(name = "Follower_username")
    private String follower;
    @Id
    @Column(name = "followed_username")
    private String followed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "follower_username", referencedColumnName = "username", insertable = false, updatable = false)
    private User followerUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "followed_username", referencedColumnName = "username", insertable = false, updatable = false)
    private User followedUser;

    public FollowEntity() {
    }

    public FollowEntity(String follower, String followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public User getFollowerUser() {
        return followerUser;
    }

    public void setFollowerUser(User followerUser) {
        this.followerUser = followerUser;
    }

    public User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }

}
