package zone.god.blogprojectbe.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zone.god.blogprojectbe.model.Blog;
import zone.god.blogprojectbe.model.BlogForm;
import zone.god.blogprojectbe.model.Tag;
import zone.god.blogprojectbe.service.BlogService;
import zone.god.blogprojectbe.service.TagService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class BlogRestController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;

    @PostMapping(value = "/newBlog")
    public ResponseEntity<Blog> addBlog(@RequestBody BlogForm blogForm){
//        List<Tag> tags = tagService.findAllById(blogForm.getTagList());
        Blog blog = new Blog();
        blog.setTittle(blogForm.getTittle());
        blog.setDescription(blogForm.getDescription());
//        blog.setTagList(tags);
        blog.setContent(blogForm.getContent());
        blogService.save(blog);
        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }

    @GetMapping("/blogList")
    public ResponseEntity<List<Blog>> getBlogList() {
        List<Blog> blogList = blogService.findAll();
        return new ResponseEntity<>(blogList, HttpStatus.OK);
    }

    @PutMapping("/updateBlog")
    public ResponseEntity<Blog> updateBlog(@RequestBody Blog blog) {
        blogService.save(blog);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBlog/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable("id") long id) {
        blogService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
