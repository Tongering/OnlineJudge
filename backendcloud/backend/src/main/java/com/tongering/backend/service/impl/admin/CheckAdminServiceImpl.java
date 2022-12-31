package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.AdminMapper;
import com.tongering.backend.pojo.Admin;
import com.tongering.backend.pojo.Teacher;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.admin.CheckAdminService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckAdminServiceImpl implements CheckAdminService {
    @Autowired
    private AdminMapper mapper;

    @Override
    public Map<String, String> check() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();

        Integer userId = user.getId();
        Admin admin = mapper.selectById(userId);

        Map<String, String> data = new HashMap<>();

        if(admin == null){ // 当前用户不是管理员
            data.put("error_message", "admin_false");
        } else {
            data.put("error_message", "admin_true");
        }
        return data;
    }
}
