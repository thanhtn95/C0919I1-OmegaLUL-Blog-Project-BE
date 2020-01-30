package zone.god.blogprojectbe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BlogForm implements Serializable {
    private long id;
    private String tittle;
    private String description;
    private String thumbnail;
    private Iterable<Long> tagList;
    private String content;
    private String createdDate;
    private String lastUpdatedDate;
    private String username;
    @JsonProperty
    private boolean isPrivate;
    public BlogForm() {
    }

}
