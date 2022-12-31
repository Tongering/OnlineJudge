package com.tongering.backend.service.impl;

import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.GetUserProfileService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetUserProfileServiceImpl implements GetUserProfileService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Override
    public Map<String, String> getProfile() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        Map<String, String> result = new HashMap<>();

        Users users = usersMapper.selectById(user.getId());
        UserProfile profile = userProfileMapper.selectById(user.getId());
        result.put("username", users.getUsername());
        result.put("mail", profile.getEmail());
        result.put("description", profile.getDeclaration());

        return result;
    }
}
