package com.tongering.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public Map<String, String> register(Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");
        String confirmedPassword = data.get("confirmedPassword");

        Map<String, String> map = new HashMap<>();

        if(username == null){
            map.put("error_message", "用户名不能为空");
            return map;
        }

        if(password == null || confirmedPassword == null){
            map.put("error_message", "密码不能为空");
        }

        username = username.trim();

        if(username.length() == 0){
            map.put("error_message", "用户名不能为空");
            return map;
        }

        if(password.length() == 0 || confirmedPassword.length() == 0){
            map.put("error_message", "密码不能为空");
            return map;
        }

        if(username.length() > 100){
            map.put("error_message", "用户名长度不能大于100");
            return map;
        }

        if(password.length() > 100 || confirmedPassword.length() > 100){
            map.put("error_message", "密码长度不能超过100");
            return map;
        }

        if(!password.equals(confirmedPassword)){
            map.put("error_message", "两次密码不一致");
            return map;
        }

        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<Users> users = usersMapper.selectList(queryWrapper);

        if(!users.isEmpty()){
            map.put("error_message", "用户已存在");
            return map;
        }

        String encodedpassword = passwordEncoder.encode(password);
        String photo = "https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/202210061426705.jpg";
        String email = "这家伙很懒，邮箱也没留";
        String declaration = "这家伙连介绍都没写";
        String background = "https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/202210071350211.jpg";

        Users user = new Users(null, username, encodedpassword);
        usersMapper.insert(user);

        UserProfile userProfile = new UserProfile(user.getId(), photo, email, declaration, new Date(), background);
        userProfileMapper.insert(userProfile);

        map.put("error_message", "success");
        System.out.println("新填一位成员：" + username);
        return map;
    }
}
