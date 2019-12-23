package zone.god.blogprojectbe.model;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String tittle;
    private String description;
    private String content;

    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE})
    @JoinTable(
            name = "blog_tag",
            joinColumns = {@JoinColumn(name = "blog_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tagList;

    public Blog() {
    }

    public Blog(String tittle, String description, String content, List<Tag> tagList) {
        this.tittle = tittle;
        this.description = description;
        this.content = content;
        this.tagList = tagList;
    }
}
