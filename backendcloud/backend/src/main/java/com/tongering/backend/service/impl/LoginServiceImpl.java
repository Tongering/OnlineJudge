package com.tongering.backend.service.impl;

import com.tongering.backend.mapper.UserIpMapper;
import com.tongering.backend.pojo.UserIp;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.LoginService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import com.tongering.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserIpMapper userIpMapper;

    @Override
    public Map<String, String> getToken(String username, String password, String ip) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        UserDetailsImpl loginuser = (UserDetailsImpl) authenticate.getPrincipal();

        Users user = loginuser.getUsers();

        String jwt = JwtUtil.createJWT(user.getId().toString());

        userIpMapper.insert(new UserIp(null, user.getId(), ip, new Date()));
        System.out.println("ID:" + user.getId() + "的" + user.getUsername() + "在" + ip + "登录");

        Map<String, String> map = new HashMap<>();

        map.put("error_message", "success");
        map.put("token", jwt);

        return map;
    }
}
