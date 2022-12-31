package com.tongering.backend.service.impl.image;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.service.image.UploadPhotoService;
import com.tongering.backend.utils.UploadImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class UploadPhotoServiceImpl implements UploadPhotoService {
    @Autowired
    private UserProfileMapper mapper;
    @Override
    public Map<String, String> upload(MultipartFile file, Integer userId) {
        Map<String, String> result = new HashMap<>();
        String type = "photo";
        String photoUrl = UploadImageUtil.uploadimage(file, userId, type);
        try{
            UpdateWrapper<UserProfile> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", userId).set("photo", photoUrl);
            mapper.update(null, wrapper);
            result.put("error_messgae", "success");
        } catch (Exception e){
            e.printStackTrace();
            result.put("error_message", "error");
        }
        return result;
    }
}
