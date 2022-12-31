package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.mapper.ExerciseTypeMapper;
import com.tongering.backend.pojo.ExercisesType;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.admin.CheckAdminService;
import com.tongering.backend.service.exercise.ModifyExerciseTypeService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModifyExerciseTypeServiceImpl implements ModifyExerciseTypeService {
    @Autowired
    private ExerciseTypeMapper typeMapper;
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;
    @Autowired
    private CheckAdminService checkAdminService;
    @Override
    public Map<String, String> modify(String typeName, Integer id) {
        Map<String, String> result = new HashMap<>();
        UpdateWrapper<ExercisesType> updateWrapper = new UpdateWrapper<>();
        if(checkAdminService.check().get("error_message").equals("admin_true")){
            updateWrapper.eq("id", id).set("typename", typeName);
            typeMapper.update(null, updateWrapper);
            result.put("error_message", "success");
            return result;
        }
        if(checkIsTeacherService.check().get("error_message").equals("teacher_true")){
            UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
            updateWrapper.eq("id", id).eq("create_id",
                    loginuser.getUsers().getId()).set("typename", typeName);
            typeMapper.update(null, updateWrapper);
            result.put("error_message", "success");
            return result;
        }
        result.put("error_message", "error");
        return result;
    }
}
