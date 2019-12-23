package zone.god.blogprojectbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zone.god.blogprojectbe.model.Blog;

public interface BlogREpository extends JpaRepository<Blog,Long> {
}
