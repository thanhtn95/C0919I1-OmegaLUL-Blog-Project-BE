package zone.god.blogprojectbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zone.god.blogprojectbe.model.ImageBlog;
import zone.god.blogprojectbe.repository.ImageBlogRepository;

import java.util.List;

@Service
public class ImageBlogServiceImpl implements ImageBlogService {

    @Autowired
    private ImageBlogRepository imageBlogRepository;

    @Override
    public void save(ImageBlog imageBlog) {
        imageBlogRepository.save(imageBlog);
    }

    @Override
    public List<ImageBlog> findAll() {
        return imageBlogRepository.findAll();
    }

    @Override
    public void deleteImageBlog(Long id) {
        imageBlogRepository.deleteById(id);
    }

    @Override
    public ImageBlog findById(Long id) {
        if (imageBlogRepository.findById(id).isPresent()) {
            return imageBlogRepository.findById(id).get();
        }
        return null;
    }
}
