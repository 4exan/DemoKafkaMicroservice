package ua.kusakabe.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.kusakabe.entity.Follow;
import ua.kusakabe.entity.FollowId;
import ua.kusakabe.entity.Post;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
//    @Query("SELECT f FROM Follow f JOIN FETCH f.followed u LEFT JOIN FETCH u.posts WHERE f.follower.username = :username")
@EntityGraph(attributePaths = {"followerUser", "followedUser"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Follow> findAllByFollower(@Param("username") String follower);
    @EntityGraph(attributePaths = {"followerUser", "followedUser"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Follow> findAllByFollowed(@Param("username") String username);
    @EntityGraph(attributePaths = {"followerUser", "followedUser"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Follow> findByFollowerAndFollowed(@Param("follower") String follower, @Param("followed") String followed);
}
