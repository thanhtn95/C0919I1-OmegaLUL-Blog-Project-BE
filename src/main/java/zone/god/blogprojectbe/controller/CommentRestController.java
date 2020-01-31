package zone.god.blogprojectbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zone.god.blogprojectbe.model.Blog;
import zone.god.blogprojectbe.model.Comment;
import zone.god.blogprojectbe.model.CommentForm;
import zone.god.blogprojectbe.model.User;
import zone.god.blogprojectbe.model.message.response.ResponseMessage;
import zone.god.blogprojectbe.service.BlogService;
import zone.god.blogprojectbe.service.CommentService;
import zone.god.blogprojectbe.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class CommentRestController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;

    @PostMapping("/newComment")
    public ResponseEntity<?> addNewComment(@RequestBody CommentForm commentForm) {
        User user = userService.findByUsername(commentForm.getUsername()).get();
        Blog blog = blogService.findById(commentForm.getBlogId());
        Comment comment = new Comment();
        comment.setComment(commentForm.getComment());
        comment.setUser(user);
        comment.setBlog(blog);
        commentService.save(comment);
        return new ResponseEntity<>(new ResponseMessage("Added comment"), HttpStatus.ACCEPTED);
    }
}
