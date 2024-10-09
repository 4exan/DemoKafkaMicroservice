package ua.kusakabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kusakabe.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
