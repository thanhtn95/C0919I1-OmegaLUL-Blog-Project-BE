package zone.god.blogprojectbe.model;

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
    private String createdDate;
    private String imageUrls;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ImageBlog() {
    }
}
