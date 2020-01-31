package zone.god.blogprojectbe.model;

import lombok.Data;

@Data
public class CommentForm {
    private long id;
    private String comment;
    private String username;
    private long blogId;

    public CommentForm() {
    }

    public CommentForm(long id, String comment, String username, long blogId) {
        this.id = id;
        this.comment = comment;
        this.username = username;
        this.blogId = blogId;
    }
}
