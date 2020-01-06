package zone.god.blogprojectbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zone.god.blogprojectbe.model.Blog;

import java.util.List;

public interface BlogREpository extends JpaRepository<Blog,Long> {
    List<Blog> findAllByTittleContaining(String keyWord);
}
