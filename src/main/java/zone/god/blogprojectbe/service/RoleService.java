package zone.god.blogprojectbe.service;

import zone.god.blogprojectbe.model.Role;
import zone.god.blogprojectbe.model.RoleName;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleName roleName);
}
