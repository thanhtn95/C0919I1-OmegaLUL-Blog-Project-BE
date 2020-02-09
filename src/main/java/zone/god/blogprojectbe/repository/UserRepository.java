package zone.god.blogprojectbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zone.god.blogprojectbe.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    Boolean existsByDisplayName(String displayName);
}
