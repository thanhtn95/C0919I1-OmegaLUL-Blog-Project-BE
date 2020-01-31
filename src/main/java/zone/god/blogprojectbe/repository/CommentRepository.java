package zone.god.blogprojectbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zone.god.blogprojectbe.model.Blog;
import zone.god.blogprojectbe.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBlog(Blog blog);
}
