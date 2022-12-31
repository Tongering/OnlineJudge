package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.AdminMapper;
import com.tongering.backend.service.admin.DeleteAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteAdminServiceImpl implements DeleteAdminService {
    @Autowired
    private AdminMapper mapper;

    @Override
    public Map<String, String> delete(Integer userId) {
        Map<String, String> result = new HashMap<>();
        mapper.deleteById(userId);
        result.put("error_message", "success");
        return result;
    }
}
