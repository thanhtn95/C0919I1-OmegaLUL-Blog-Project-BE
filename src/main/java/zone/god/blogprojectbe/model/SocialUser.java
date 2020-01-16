package zone.god.blogprojectbe.model;

import lombok.Data;

@Data
public class SocialUser {
    public String name;
    public String email;
    public String provider;
    public String provideid;
    public String image;
    public String token;
    public String idToken;

    public SocialUser() {
    }

    public SocialUser(String name, String email, String provider, String provideid, String image, String token, String idToken) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.provideid = provideid;
        this.image = image;
        this.token = token;
        this.idToken = idToken;
    }
}
