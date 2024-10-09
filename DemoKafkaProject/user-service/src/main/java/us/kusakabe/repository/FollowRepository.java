package us.kusakabe.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import us.kusakabe.entity.Follow;
import us.kusakabe.entity.FollowId;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    @EntityGraph(attributePaths = {"followerUser", "followedUser"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Follow> findByFollowerAndFollowed(@Param("follower") String follower,@Param("followed") String followed);

}
