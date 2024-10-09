package ua.kusakabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kusakabe.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUsername(String username);
}
