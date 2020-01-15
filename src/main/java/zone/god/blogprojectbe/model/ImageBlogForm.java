package zone.god.blogprojectbe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ImageBlogForm implements Serializable {
    private long id;
    private String tittle;
    private String description;
    private String createdDate;
    private String lastUpdatedDate;
    private String imageUrls;
    private String username;
    @JsonProperty
    private boolean isPrivate;

    public ImageBlogForm() {
    }

}
