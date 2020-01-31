package zone.god.blogprojectbe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.math.BigInteger;
import java.util.Date;
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
    private String thumbnail;
    private String content;
    private String createdDate;
    private String lastUpdatedDate;
    private long view;
    @JsonProperty
    private boolean isPrivate;

    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE} ,fetch = FetchType.EAGER)
    @JoinTable(
            name = "blog_tag",
            joinColumns = {@JoinColumn(name = "blog_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tagList;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Blog() {
    }

    public Blog(String tittle, String description, String content, List<Tag> tagList) {
        this.tittle = tittle;
        this.description = description;
        this.content = content;
        this.tagList = tagList;
    }
}
