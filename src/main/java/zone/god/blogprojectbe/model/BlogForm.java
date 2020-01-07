package zone.god.blogprojectbe.model;

import lombok.Data;

import java.util.Date;

@Data
public class BlogForm {
    private long id;
    private String tittle;
    private String description;
    private String thumbnail;
    private Iterable<Long> tagList;
    private String content;
    private String createdDate;
    private String lastUpdatedDate;
    private String username;

    public BlogForm() {
    }
}
