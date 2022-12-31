package com.tongering.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.UpdateProfileService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateProfileServiceImpl implements UpdateProfileService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        Map<String, String> result = new HashMap<>();

        String username = data.get("username");
        String mail = data.get("mail");
        String description = data.get("description");

        UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<>();
        usersUpdateWrapper.eq("id" ,user.getId()).set("username", username);
        UpdateWrapper<UserProfile> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", user.getId()).set("email", mail).set("declaration", description);
        try{
            usersMapper.update(null, usersUpdateWrapper);
            userProfileMapper.update(null, updateWrapper);
            result.put("error_message", "success");
        } catch (Exception e){
            e.printStackTrace();
            result.put("error_message", "error");
        }


        return result;
    }
}
