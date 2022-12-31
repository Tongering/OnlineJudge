package com.tongering.backend.service.impl.exercise;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tongering.backend.mapper.ExerciseTypeMapper;
import com.tongering.backend.pojo.ExercisesType;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.admin.CheckAdminService;
import com.tongering.backend.service.exercise.AddExerciseTypeService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddExerciseTypeServiceImpl implements AddExerciseTypeService {
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;
    @Autowired
    private CheckAdminService checkAdminService;
    @Autowired
    private ExerciseTypeMapper typeMapper;
    @Override
    public Map<String, String> add(String typeName) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        Map<String, String> result = new HashMap<>();

        if(checkIsTeacherService.check().get("error_message").equals("teacher_true")){ // 当前用户是教师
            typeMapper.insert(new ExercisesType(null, user.getId(), typeName, null));
            result.put("error_message", "success");
            return result;
        }
        if(checkAdminService.check().get("error_message").equals("admin_true")){ //当前用户是管理员
            typeMapper.insert(new ExercisesType(null, user.getId(), typeName, null));
            result.put("error_message", "success");
            return result;
        }
        result.put("error_message", "error");
        return result;
    }
}
