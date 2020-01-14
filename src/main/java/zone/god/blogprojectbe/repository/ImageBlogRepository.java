package zone.god.blogprojectbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zone.god.blogprojectbe.model.ImageBlog;

public interface ImageBlogRepository extends JpaRepository<ImageBlog,Long> {
}
