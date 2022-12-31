package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.UserTerminalMapper;
import com.tongering.backend.pojo.UserTerminal;
import com.tongering.backend.service.admin.DeleteTerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteTerminalServiceImpl implements DeleteTerminalService {
    @Autowired
    private UserTerminalMapper mapper;

    @Override
    public Map<String, String> delete(Integer userId) {
        Map<String, String> result = new HashMap<>();
        mapper.deleteById(userId);
        result.put("error_message","success");

        return result;
    }
}
