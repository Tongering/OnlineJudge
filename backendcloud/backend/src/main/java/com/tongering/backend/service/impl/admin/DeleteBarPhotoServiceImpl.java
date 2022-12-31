package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.BarPhotoMapper;
import com.tongering.backend.service.admin.DeleteBarPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteBarPhotoServiceImpl implements DeleteBarPhotoService {
    @Autowired
    private BarPhotoMapper mapper;

    @Override
    public Map<String, String> delete(Map<String, String> data) {
        Map<String, String> result = new HashMap<>();
        Integer id = Integer.parseInt(data.get("id"));
        mapper.deleteById(id);
        result.put("error_message", "success");
        return result;
    }
}
