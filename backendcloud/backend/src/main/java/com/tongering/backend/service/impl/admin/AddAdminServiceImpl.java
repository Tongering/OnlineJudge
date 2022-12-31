package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.AdminMapper;
import com.tongering.backend.pojo.Admin;
import com.tongering.backend.service.admin.AddAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddAdminServiceImpl implements AddAdminService {
    @Autowired
    private AdminMapper mapper;

    @Override
    public Map<String, String> add(Integer userId) {
        Map<String, String> result = new HashMap<>();
        mapper.insert(new Admin(userId));
        result.put("error_message", "success");

        return result;
    }
}
