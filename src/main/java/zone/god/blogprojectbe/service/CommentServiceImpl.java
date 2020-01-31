package zone.god.blogprojectbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zone.god.blogprojectbe.model.Blog;
import zone.god.blogprojectbe.model.Comment;
import zone.god.blogprojectbe.repository.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments;
    }

    @Override
    public List<Comment> findAllByBlog(Blog blog) {
        List<Comment> comments = commentRepository.findAllByBlog(blog);
        return comments;
    }

    @Override
    public void delete(long id) {
        commentRepository.deleteById(id);
    }
}
