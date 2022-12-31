package com.tongering.backend.service.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadPhotoService {
    public Map<String, String> upload(MultipartFile file, Integer userId);
}
