package com.eventio.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String upload(MultipartFile multipartFile) throws IOException;
}
