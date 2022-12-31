package com.tongering.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.TeacherStudentRelationshipMapper;
import com.tongering.backend.pojo.TeacherStudentRelationship;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.AddTeacherRelationshipService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddTeacherRelationshipRelationshipServiceImpl implements AddTeacherRelationshipService {
    @Autowired
    private TeacherStudentRelationshipMapper mapper;
    @Override
    public Map<String, String> add(Integer userId) {
        Map<String, String> result = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        QueryWrapper<TeacherStudentRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", userId);
        if(userId.equals(user.getId())){
            result.put("error_message", "same");
        } else {
            if(mapper.selectOne(queryWrapper)!=null ){
                if(mapper.selectOne(queryWrapper).getStudentId().equals(userId)){
                    result.put("error_message", "error");
                } else {
                    mapper.insert(new TeacherStudentRelationship(userId, user.getId()));
                    result.put("error_message", "success");
                }

            } else {
                mapper.insert(new TeacherStudentRelationship(userId, user.getId()));
                result.put("error_message", "success");
            }
        }

        return result;
    }
}
