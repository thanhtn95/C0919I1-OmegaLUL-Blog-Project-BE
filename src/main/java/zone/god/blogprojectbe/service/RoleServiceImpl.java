package zone.god.blogprojectbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zone.god.blogprojectbe.model.Role;
import zone.god.blogprojectbe.model.RoleName;
import zone.god.blogprojectbe.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }
}
