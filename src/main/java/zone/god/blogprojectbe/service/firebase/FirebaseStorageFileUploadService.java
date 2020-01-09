package zone.god.blogprojectbe.service.firebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zone.god.blogprojectbe.JsonPayload.JPayload;
import zone.god.blogprojectbe.constants.ApplicationConstants;

import java.io.IOException;

@Service
public class FirebaseStorageFileUploadService {
    @Autowired
    private FirebaseStorageUploadService firebaseStorageUploadService;

    public JPayload uploadFileToFirebaseStorage(MultipartFile multipartFile) {
        try {
            String contentType = multipartFile.getContentType();
            String storageDir;
            if (contentType.startsWith("image")) {
                storageDir = "images";
            } else {
                storageDir = "files";
            }
            String downloadUrl = firebaseStorageUploadService.upload(
                        ApplicationConstants.BASE_PACKAGE_NAME + "/" + storageDir,
                    multipartFile.getOriginalFilename(), multipartFile.getBytes(), contentType);
//            System.out.println(downloadUrl);
            return new JPayload(1,multipartFile.getOriginalFilename(),downloadUrl);
        } catch (IOException e) {
            return null;
        }
    }
}
