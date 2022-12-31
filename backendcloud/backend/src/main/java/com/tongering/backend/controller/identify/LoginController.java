package com.tongering.backend.controller.identify;

import com.tongering.backend.mapper.UserIpMapper;
import com.tongering.backend.pojo.UserIp;
import com.tongering.backend.service.LoginService;
import com.tongering.backend.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/api/user/login/token/")
    public Map<String, String> getToken (@RequestParam Map<String, String> map, HttpServletRequest request){
        String username = map.get("username");
        String password = map.get("password");
        String ipAddress = IpUtil.getIpAddr(request);

        return loginService.getToken(username, password, ipAddress);
    }

}
