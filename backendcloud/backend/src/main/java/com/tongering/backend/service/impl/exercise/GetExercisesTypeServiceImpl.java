package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.ExerciseTypeMapper;
import com.tongering.backend.pojo.ExercisesType;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.admin.CheckAdminService;
import com.tongering.backend.service.exercise.GetExercisesTypeService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetExercisesTypeServiceImpl implements GetExercisesTypeService {
    @Autowired
    private ExerciseTypeMapper exerciseTypeMapper;
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;
    @Autowired
    private CheckAdminService checkAdminService;

    @Override
    public List<ExercisesType> gettype() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        if(checkAdminService.check().get("error_message").equals("admin_true")){

            return exerciseTypeMapper.selectList(null);
        }
        if(checkIsTeacherService.check().get("error_message").equals("teacher_true")){

            QueryWrapper<ExercisesType> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("create_id", user.getId());
            return exerciseTypeMapper.selectList(queryWrapper);
        }

        return exerciseTypeMapper.selectList(null);
    }
}
