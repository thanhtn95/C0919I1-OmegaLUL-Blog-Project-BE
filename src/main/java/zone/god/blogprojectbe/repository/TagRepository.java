package zone.god.blogprojectbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zone.god.blogprojectbe.model.Tag;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
