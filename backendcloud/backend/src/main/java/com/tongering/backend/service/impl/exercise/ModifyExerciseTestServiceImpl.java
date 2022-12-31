package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.mapper.ExerciseTestMapper;
import com.tongering.backend.pojo.ExerciseTest;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.exercise.ModifyExerciseTestService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ModifyExerciseTestServiceImpl implements ModifyExerciseTestService {
    @Autowired
    private ExerciseTestMapper testMapper;
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;

    @Override
    public Map<String, String> modifytest(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();

        Map<String, String> isTeacher = checkIsTeacherService.check();

        if(isTeacher.get("error_message").equals("admin_false")){
            return isTeacher;
        }

        Map<String, String> result = new HashMap<>();
        Integer id = Integer.parseInt(data.get("id"));
        String input = data.get("input");
        String output = data.get("output");

        UpdateWrapper<ExerciseTest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).eq("create_id", user.getId()).set("input", input).set("output", output);



        result.put("error_message", "success");
        return result;
    }
}
