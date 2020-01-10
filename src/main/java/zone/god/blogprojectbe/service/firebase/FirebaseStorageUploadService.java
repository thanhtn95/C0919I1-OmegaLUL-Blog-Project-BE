package zone.god.blogprojectbe.service.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.StorageException;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FirebaseStorageUploadService {
    @Value("${application.firebase.storage.api}")
    private String googleStorageApi;

    public String upload(String dir, String fileName,
                         byte[] data, String contentType) throws StorageException {
        Blob file = StorageClient.getInstance().bucket()
                .create(dir + "/" + fileName, data, contentType);
        if(contentType.startsWith("image")){
            return googleStorageApi + "/" + file.getBucket() + "/zone.god.blogprojectbe/images/" + fileName;
        }
        return googleStorageApi + "/" + file.getBucket() + "/zone.god.blogprojectbe/files/" + fileName;
    }
}
