package zone.god.blogprojectbe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zone.god.blogprojectbe.model.ImageBlog;

@RestController
@RequestMapping("/api")
public class ImageBlogRestController {
    @PostMapping("/newImageBlog")
    public ResponseEntity<ImageBlog> newImageBlog(@RequestParam("info") String imageBlogInfo, @RequestParam("files")MultipartFile[] files) {
        return null;
    }
}
