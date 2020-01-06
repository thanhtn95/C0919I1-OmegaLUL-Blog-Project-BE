package zone.god.blogprojectbe.model.message.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class LoginForm {
    @NotBlank
    @Size(min=3, max = 60)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public LoginForm() {
    }

    public LoginForm(@NotBlank @Size(min = 3, max = 60) String username, @NotBlank @Size(min = 6, max = 40) String password) {
        this.username = username;
        this.password = password;
    }
}
