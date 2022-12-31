package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.UserTerminalMapper;
import com.tongering.backend.pojo.UserTerminal;
import com.tongering.backend.service.admin.AddTerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddTerminalServiceImpl implements AddTerminalService {
    @Autowired
    private UserTerminalMapper terminalMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("userId"));
        String ip = data.get("ip");
        Integer port = Integer.parseInt(data.get("port"));
        String terminalUsername = data.get("terminalUsername");
        String terminalPassword = data.get("terminalPassword");
        terminalMapper.insert(new UserTerminal(userId, ip, port, terminalUsername, terminalPassword));
        Map<String, String> result = new HashMap<>();
        result.put("error_message", "success");
        return result;
    }
}
