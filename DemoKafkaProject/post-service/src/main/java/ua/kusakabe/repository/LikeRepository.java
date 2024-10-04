package ua.kusakabe.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.kusakabe.entity.Like;
import ua.kusakabe.entity.LikeId;
import ua.kusakabe.entity.User;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
    //Get all users who liked post
    List<Like> findAllByPost_Id(long post);
    //Get all posts that user liked
    @EntityGraph(attributePaths = {"post", "user"})
    List<Like> findAllByUsername(@Param("username")String username);
    //For check is post liked -> if present: post already liked
    Optional<Like> findByUsernameAndPost_Id(String username, long post);
}
