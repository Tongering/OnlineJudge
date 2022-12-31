package com.tongering.backend.service.impl.image;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.service.image.UploadImageService;
import com.tongering.backend.utils.UploadImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
@Service
public class UploadImageServiceImpl implements UploadImageService {
    @Override
    public String upload(MultipartFile file, Integer userId) {
        String type = "invitation";
        System.out.println(file);
        return UploadImageUtil.uploadimage(file, userId, type);
    }
}
