package zone.god.blogprojectbe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
    public Comment() {
    }
}
