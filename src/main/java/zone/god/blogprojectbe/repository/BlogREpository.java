package zone.god.blogprojectbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zone.god.blogprojectbe.model.Blog;
import zone.god.blogprojectbe.model.Tag;
import zone.god.blogprojectbe.model.User;

import java.util.List;

public interface BlogREpository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByTittleContaining(String keyWord);

    List<Blog> findAllByUser(User user);

    List<Blog> findTop5ByOrderByViewDesc();
    List<Blog> findTop5ByTagListContainingOrderByViewDesc(Tag tag);
}
