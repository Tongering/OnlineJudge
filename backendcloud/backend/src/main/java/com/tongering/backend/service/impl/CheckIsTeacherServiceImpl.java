package com.tongering.backend.service.impl;

import com.tongering.backend.mapper.TeacherMapper;
import com.tongering.backend.pojo.Teacher;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckIsTeacherServiceImpl implements CheckIsTeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Map<String, String> check() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();

        Integer userId = user.getId();
        Teacher teacher = teacherMapper.selectById(userId);

        Map<String, String> data = new HashMap<>();

        if(teacher == null){ // 当前用户不是管理员
            data.put("error_message", "teacher_false");
        } else {
            data.put("error_message", "teacher_true");
        }

        return data;
    }
}
