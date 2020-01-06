package zone.god.blogprojectbe.JsonPayload;

import lombok.Data;

import java.io.Serializable;

@Data
public class JPayload implements Serializable {
    private long uploaded;
    private String fileName;
    private String url;

    public JPayload() {
    }

    public JPayload(long uploaded, String fileName, String url) {
        this.uploaded = uploaded;
        this.fileName = fileName;
        this.url = url;
    }
}
