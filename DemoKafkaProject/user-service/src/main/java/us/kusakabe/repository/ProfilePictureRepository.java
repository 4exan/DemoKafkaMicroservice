package us.kusakabe.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.kusakabe.entity.ProfilePicture;

import java.util.Optional;

@Repository
@Transactional
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {
    Optional<ProfilePicture> findByUsername(String username);
}
