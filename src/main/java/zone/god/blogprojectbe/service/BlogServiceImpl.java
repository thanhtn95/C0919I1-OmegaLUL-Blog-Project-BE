package zone.god.blogprojectbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zone.god.blogprojectbe.model.Blog;
import zone.god.blogprojectbe.model.Tag;
import zone.god.blogprojectbe.model.User;
import zone.god.blogprojectbe.repository.BlogREpository;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogREpository blogREpository;

    @Override
    public List<Blog> findAll() {
        return blogREpository.findAll();
    }

    @Override
    public Blog findById(long id) {
        if (blogREpository.findById(id).isPresent()) {
            return blogREpository.findById(id).get();
        }
        return null;
    }

    @Override
    public void save(Blog blog) {
        blogREpository.save(blog);
    }

    @Override
    public List<Blog> findByTittle(String keyWord) {
        return blogREpository.findAllByTittleContaining(keyWord);
    }

    @Override
    public void delete(long id) {
        blogREpository.deleteById(id);
    }

    @Override
    public List<Blog> findByUser(User user) {
        return blogREpository.findAllByUser(user);
    }

    @Override
    public List<Blog> findTop5ByOrderByViewDesc() {
        return blogREpository.findTop5ByOrderByViewDesc();
    }

    @Override
    public List<Blog> findTop5ByTagListContainingOrderByViewDesc(Tag tag) {
        return blogREpository.findTop5ByTagListContainingOrderByViewDesc(tag);
    }
}
