package zone.god.blogprojectbe.JsonPayload;

import lombok.Data;

@Data
public class JPayload {
    private int uploaded;
    private String  fileName, url;

    public JPayload() {
    }

    public JPayload(int uploaded, String fileName, String url) {
        this.uploaded = uploaded;
        this.fileName = fileName;
        this.url = url;
    }
}
