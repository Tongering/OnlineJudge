package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.BarPhotoMapper;
import com.tongering.backend.pojo.BarPhoto;
import com.tongering.backend.service.admin.AddBarPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddBarPhotoServiceImpl implements AddBarPhotoService {
    @Autowired
    private BarPhotoMapper mapper;
    @Override
    public Map<String, String> add(Map<String, String> data) {
        Map<String, String> result = new HashMap<>();

        Integer id = Integer.parseInt(data.get("id"));
        String url = data.get("url");
        mapper.insert(new BarPhoto(id, url));


        result.put("error_message", "success");
        return result;
    }
}
