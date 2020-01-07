package zone.god.blogprojectbe.service;

import zone.god.blogprojectbe.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    void saveUser(User user);
}
