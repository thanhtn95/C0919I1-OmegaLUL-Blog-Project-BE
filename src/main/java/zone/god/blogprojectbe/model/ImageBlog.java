package zone.god.blogprojectbe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "imageBlogs")
@Data
public class ImageBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String tittle;
    private String description;
    private String createdDate;
    private String lastUpdatedDate;
    private String imageUrls;
    private int view;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JsonProperty
    private boolean isPrivate;

    public ImageBlog() {
    }
}
