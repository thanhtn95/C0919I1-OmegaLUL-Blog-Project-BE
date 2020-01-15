package zone.god.blogprojectbe.service;

import zone.god.blogprojectbe.model.ImageBlog;

import java.util.List;

public interface ImageBlogService {
    void save(ImageBlog imageBlog);
    List<ImageBlog> findAll();
    void deleteImageBlog(Long id);
    ImageBlog findById(Long id);
}
