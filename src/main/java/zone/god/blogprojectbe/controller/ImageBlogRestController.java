package zone.god.blogprojectbe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zone.god.blogprojectbe.model.ImageBlog;
import zone.god.blogprojectbe.model.ImageBlogForm;
import zone.god.blogprojectbe.model.User;
import zone.god.blogprojectbe.model.message.response.ResponseMessage;
import zone.god.blogprojectbe.service.ImageBlogService;
import zone.god.blogprojectbe.service.UserService;
import zone.god.blogprojectbe.service.firebase.FirebaseStorageFileUploadService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ImageBlogRestController {
    @Autowired
    private ImageBlogService imageBlogService;
    @Autowired
    private FirebaseStorageFileUploadService firebaseStorageFileUploadService;
    @Autowired
    private UserService userService;

    @PostMapping("/newImageBlog")
    public ResponseEntity<?> newImageBlog(@RequestParam("imageBlogInfo") String imageBlogInfo, @RequestParam("images") Optional<MultipartFile[]> files) throws JsonProcessingException {
        if (!files.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("No Input Image"), HttpStatus.BAD_REQUEST);
        }
        ImageBlogForm imageBlogForm = new ObjectMapper().readValue(imageBlogInfo, ImageBlogForm.class);
        String imgUrls = firebaseStorageFileUploadService.uploadMultipleFileToFireBase(files.get());
        ImageBlog imageBlog = new ImageBlog();
        imageBlog.setImageUrls(imgUrls);
        setDataFromForm(imageBlogForm, imageBlog);
        imageBlogService.save(imageBlog);
        return new ResponseEntity<>(imageBlog, HttpStatus.OK);
    }

    @GetMapping("/imagBlogList")
    public ResponseEntity<List<ImageBlog>> getImageBlogList() {
        List<ImageBlog> imageBlogs = imageBlogService.findAll();
        return new ResponseEntity<>(imageBlogs, HttpStatus.OK);
    }

    @GetMapping("/imageBlog/{id}")
    public ResponseEntity<ImageBlog> getImageBlog(@PathVariable("id") Long id) {
        ImageBlog imageBlog = imageBlogService.findById(id);
        if (!imageBlog.isPrivate()) {
            imageBlog.setViewCount(imageBlog.getViewCount() + 1);
            imageBlogService.save(imageBlog);
        }
        return new ResponseEntity<>(imageBlog, HttpStatus.OK);
    }

    @DeleteMapping("/deleteImgBlog/{id}")
    public ResponseEntity<ImageBlog> deleteImageBlog(@PathVariable("id") Long id) {
        ImageBlog imageBlog = imageBlogService.findById(id);
        imageBlogService.deleteImageBlog(id);
        return new ResponseEntity<>(imageBlog, HttpStatus.OK);
    }

    @PutMapping("/updateImgBlog")
    public ResponseEntity<?> updateImageBlog(@RequestParam("imageBlogInfo") String imageBlogInfo, @RequestParam("images") Optional<MultipartFile[]> files) throws JsonProcessingException {
        ImageBlogForm imageBlogForm = new ObjectMapper().readValue(imageBlogInfo, ImageBlogForm.class);
        ImageBlog imageBlog = new ImageBlog();
        imageBlog.setId(imageBlogForm.getId());
        if (files.isPresent()) {
            String imgUrls = firebaseStorageFileUploadService.uploadMultipleFileToFireBase(files.get());
            imageBlog.setImageUrls(imageBlogForm.getImageUrls() + "," + imgUrls);
        } else {
            if (imageBlogForm.getImageUrls().length() == 0) {
                return new ResponseEntity<>(new ResponseMessage("No Input Image"), HttpStatus.BAD_REQUEST);
            } else {
                imageBlog.setImageUrls(imageBlogForm.getImageUrls());
            }
        }
        setDataFromForm(imageBlogForm, imageBlog);
        imageBlogService.save(imageBlog);
        return new ResponseEntity<>(imageBlog, HttpStatus.OK);
    }

    private void setDataFromForm(ImageBlogForm imageBlogForm, ImageBlog imageBlog) {
        User user = userService.findByUsername(imageBlogForm.getUsername()).get();
        imageBlog.setUser(user);
        if (imageBlogForm.getCreatedDate() == null) {
            imageBlog.setCreatedDate(new Date() + "");
        } else {
            imageBlog.setCreatedDate(imageBlogForm.getCreatedDate());
        }
        imageBlog.setLastUpdatedDate(new Date() + "");
        imageBlog.setPrivate(imageBlogForm.isPrivate());
        imageBlog.setTittle(imageBlogForm.getTittle());
        imageBlog.setDescription(imageBlogForm.getDescription());
    }
}
