package zone.god.blogprojectbe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zone.god.blogprojectbe.JsonPayload.JPayload;
import zone.god.blogprojectbe.service.firebase.FirebaseStorageFileUploadService;

@RestController
@RequestMapping("/api")
@Api(description = "Upload tệp tin lên demo_firebase storage")
public class FirebaseStorageFileUploadController {
    @Autowired
    private FirebaseStorageFileUploadService firebaseStorageFileUploadService;

    @ApiOperation(value = "Upload tệp tin được chọn lên demo_firebase storage", response = Iterable.class)

    @PostMapping(value = "/files-upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JPayload> uploadFileToFirebaseStorage(@RequestParam(value = "upload", required = false) MultipartFile file) {

        JPayload jPayload = firebaseStorageFileUploadService.uploadFileToFirebaseStorage(file);
        if (jPayload != null) {
            return new ResponseEntity<>(jPayload, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(jPayload, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(value = "/upload-multi", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String[]> uploadMultipleToFireBase(@RequestParam("upload") MultipartFile[] files) {
        String result = firebaseStorageFileUploadService.uploadMultipleFileToFireBase(files);
        String[] test = result.split(",");
        if (result != "") {
            return new ResponseEntity<String[]>(test, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
}
