package com.eventio.backend.service.impl;

import com.eventio.backend.service.FileService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${firebase.bucket}") // Postavite putanju za download slika u application.properties
    private String bucketName;

    private String uploadFile(File file, String fileName) throws IOException {
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/%s?alt=media";
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/firebase/eventio-slike-188db-firebase-adminsdk-rdzzd-cc85d56f2a.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();                        // to get original file name
        fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

        File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
        String temp_url = this.uploadFile(file, fileName);                            // to get uploaded file link
        Files.delete(file.toPath());                                                       // to delete the copy of uploaded file stored in the project folder
        return temp_url;                                                                // Your customized response
    }

}
