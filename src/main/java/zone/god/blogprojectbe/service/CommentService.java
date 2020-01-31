package zone.god.blogprojectbe.service;

import zone.god.blogprojectbe.model.Blog;
import zone.god.blogprojectbe.model.Comment;

import java.util.List;

public interface CommentService {
    void save(Comment comment);
    List<Comment> findAll();
    List<Comment> findAllByBlog(Blog blog);
    void delete(long id);
}
