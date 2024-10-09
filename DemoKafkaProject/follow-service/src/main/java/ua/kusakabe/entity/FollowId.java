package ua.kusakabe.entity;

import java.io.Serializable;
import java.util.Objects;

public class FollowId implements Serializable {

    private String follower;
    private String followed;

    public FollowId() {
    }

    public FollowId(String follower, String followed) {
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

    @Override
    public String toString() {
        return "FollowId{" +
                "follower='" + follower + '\'' +
                ", followed='" + followed + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return Objects.equals(follower, followId.follower) && Objects.equals(followed, followId.followed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, followed);
    }
}
