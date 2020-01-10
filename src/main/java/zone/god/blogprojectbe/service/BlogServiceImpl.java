package zone.god.blogprojectbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zone.god.blogprojectbe.model.Blog;
import zone.god.blogprojectbe.repository.BlogREpository;
import zone.god.blogprojectbe.service.BlogService;

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
    public void save(Blog blog) {
        blogREpository.save(blog);
    }

    @Override
    public void delete(long id) {
        blogREpository.deleteById(id);
    }
}
