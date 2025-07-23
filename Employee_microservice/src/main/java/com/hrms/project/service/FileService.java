package com.hrms.project.service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String uploadImage(String path, MultipartFile file) throws IOException;

    //string updateImage(Long id, MultipartFile image);
}
