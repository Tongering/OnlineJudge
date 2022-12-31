package com.tongering.backend.service.impl;

import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.InfoService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private UserProfileMapper mapper;
    @Override
    public Map<String, String> getinfo() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        UserProfile userProfile = mapper.selectById(user.getId());

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("id", user.getId().toString());
        map.put("username", user.getUsername());
        map.put("photo", userProfile.getPhoto());
        map.put("background", userProfile.getBackground());

        return map;
    }
}
