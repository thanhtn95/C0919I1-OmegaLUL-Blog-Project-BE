package zone.god.blogprojectbe.service;

import org.springframework.stereotype.Service;
import zone.god.blogprojectbe.model.Blog;
import zone.god.blogprojectbe.model.Tag;
import zone.god.blogprojectbe.model.User;

import java.util.List;

@Service
public interface BlogService {
    List<Blog> findAll();

    Blog findById(long id);

    void save(Blog blog);

    List<Blog> findByTittle(String keyWord);

    void delete(long id);

    List<Blog> findByUser(User user);

    List<Blog> findTop5ByOrderByViewDesc();

    List<Blog> findTop5ByTagListContainingOrderByViewDesc(Tag tag);
}
