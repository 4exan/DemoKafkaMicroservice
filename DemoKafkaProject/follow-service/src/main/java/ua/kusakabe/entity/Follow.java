package ua.kusakabe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "follows")
@IdClass(FollowId.class)
public class Follow {

    @Id
    @Column(name = "Follower_username")
    private String follower;
    @Id
    @Column(name = "followed_username")
    private String followed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_username", referencedColumnName = "username", insertable = false, updatable = false)
//@ManyToOne(fetch = FetchType.LAZY)
//@MapsId("followerUsername")
//@JoinColumn(name = "follower_username")
    private User followerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_username", referencedColumnName = "username", insertable = false, updatable = false)
//@ManyToOne(fetch = FetchType.LAZY)
//@MapsId("followedUsername")
//@JoinColumn(name = "followed_username")
    private User followedUser;

    public Follow() {
    }

    public Follow(String follower, String followed) {
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
