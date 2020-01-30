package zone.god.blogprojectbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zone.god.blogprojectbe.model.Blog;
import zone.god.blogprojectbe.model.BlogForm;
import zone.god.blogprojectbe.model.Tag;
import zone.god.blogprojectbe.model.User;
import zone.god.blogprojectbe.service.BlogService;
import zone.god.blogprojectbe.service.TagService;
import zone.god.blogprojectbe.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class BlogRestController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/newBlog")
    public ResponseEntity<Blog> addBlog(@RequestBody BlogForm blogForm) {
        Blog blog = new Blog();
        saveToBlogFromForm(blog, blogForm);
        if (blog.getContent().equals("") || blog.getDescription().equals("") || blog.getTittle().equals("")) {
            return new ResponseEntity<>(blog, HttpStatus.EXPECTATION_FAILED);
        }
        String now = "" + new Date();
        User user = userService.findByUsername(blogForm.getUsername()).get();
        blog.setUser(user);
        blog.setCreatedDate(now);
        blog.setLastUpdatedDate(now);
        blogService.save(blog);
        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }

    @GetMapping("/blogList")
    public ResponseEntity<List<Blog>> getBlogList() {
        List<Blog> blogList = blogService.findAll();
        return new ResponseEntity<>(blogList, HttpStatus.OK);
    }

    @PutMapping("/updateBlog")
    public ResponseEntity<Blog> updateBlog(@RequestBody BlogForm blogForm) {
        Blog blog = blogService.findById(blogForm.getId());
        saveToBlogFromForm(blog, blogForm);
        User user = userService.findByUsername(blogForm.getUsername()).get();
        blog.setUser(user);
        blog.setLastUpdatedDate("" + new Date());
        blogService.save(blog);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBlog/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable("id") long id) {
        Blog blog = blogService.findById(id);
        blogService.delete(id);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable("id") long id) {
        Blog blog = blogService.findById(id);
        if (!blog.isPrivate()) {
            blog.setView(blog.getView() + 1);
            blogService.save(blog);
        }
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @GetMapping("/blog/search/{tittle}")
    public ResponseEntity<List<Blog>> searchByTittle(@PathVariable("tittle") String tittle) {
        List<Blog> blogs;
        blogs = blogService.findByTittle(tittle);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/blog/userBlogs/{username}")
    public ResponseEntity<List<Blog>> getUserBlogList(@PathVariable("username") String username) {
        User user = userService.findByUsername(username).get();
        List<Blog> blogs = blogService.findByUser(user);
        return new ResponseEntity<>(blogs, HttpStatus.ACCEPTED);
    }

    private void saveToBlogFromForm(Blog blog, BlogForm blogForm) {
        List<Tag> tags = tagService.findAllById(blogForm.getTagList());
        if (!Objects.isNull(blogForm.getId())) {
            blog.setId(blogForm.getId());
        }
        blog.setTittle(blogForm.getTittle());
        blog.setDescription(blogForm.getDescription());
        blog.setThumbnail(blogForm.getThumbnail());
        blog.setTagList(tags);
        blog.setContent(blogForm.getContent());
        blog.setPrivate(blogForm.isPrivate());
    }
}
