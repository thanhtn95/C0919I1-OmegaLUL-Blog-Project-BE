package zone.god.blogprojectbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zone.god.blogprojectbe.model.Tag;
import zone.god.blogprojectbe.service.TagService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class TagRestController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tagList")
    public ResponseEntity<List<Tag>> getTagList() {
        List<Tag> tagList = tagService.findAll();
        return new ResponseEntity<>(tagList, HttpStatus.OK);
    }

    @PostMapping("/newTag")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tag> addNewTag(@RequestBody Tag tag) {
        Tag result = tagService.addTag(tag);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
