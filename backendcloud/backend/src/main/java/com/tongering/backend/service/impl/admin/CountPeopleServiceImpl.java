package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.AdminMapper;
import com.tongering.backend.mapper.TeacherMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.admin.CheckAdminService;
import com.tongering.backend.service.admin.CountPeopleService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CountPeopleServiceImpl implements CountPeopleService {
    @Autowired
    private CheckAdminService service;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public Map<String, String> getCount() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        Map<String, String> admin = service.check();
        if(admin.get("error_message").equals("admin_false")){
            return admin;
        }
        Long allUser = usersMapper.selectCount(null);
        Long adminUser = adminMapper.selectCount(null);
        Long teacherUser = teacherMapper.selectCount(null);
        Long studentUser = (allUser - adminUser - teacherUser) * 100 / allUser;
        adminUser = adminUser * 100 / allUser;
        teacherUser = teacherUser * 100 / allUser;

        Map<String, String> result = new HashMap<>();
        result.put("admin", adminUser.toString());
        result.put("teacher", teacherUser.toString());
        result.put("student", studentUser.toString());

        return result;
    }
}
