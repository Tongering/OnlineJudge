package com.tongering.backend.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.mapper.UserTerminalMapper;
import com.tongering.backend.pojo.UserTerminal;
import com.tongering.backend.service.admin.ModifyTerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModifyTerminalServiceImpl implements ModifyTerminalService {
    @Autowired
    private UserTerminalMapper terminalMapper;

    @Override
    public Map<String, String> modify(Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("userId"));
        String ip = data.get("ip");
        Integer port = Integer.parseInt(data.get("port"));
        String terminalUsername = data.get("terminalUsername");
        String terminalPassword = data.get("terminalPassword");
        UpdateWrapper<UserTerminal> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).set("terminal_ip", ip).set("terminal_port", port)
                .set("terminal_username", terminalUsername).set("terminal_password", terminalPassword);
        Map<String, String> result = new HashMap<>();
        terminalMapper.update(null,updateWrapper);
        result.put("error_message", "success");
        return result;
    }
}
