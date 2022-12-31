package com.tongering.backend.service.impl.exercise;

import com.tongering.backend.mapper.ExerciseTestMapper;
import com.tongering.backend.pojo.ExerciseTest;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.exercise.AddExerciseTestService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddExerciseTestServiceImpl implements AddExerciseTestService {
    @Autowired
    private ExerciseTestMapper mapper;
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;

    @Override
    public Map<String, String> addtest(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();

        Map<String, String> isTeacher = checkIsTeacherService.check();

        if(isTeacher.get("error_message").equals("admin_false")){
            return isTeacher;
        }

        System.out.println(data);

        Integer exerciseId = Integer.parseInt(data.get("exerciseId"));
        String input = data.get("input");
        String output = data.get("output");
        Integer createId = user.getId();
        Date date = new Date();

        ExerciseTest test = new ExerciseTest(null, exerciseId, input, output, date, createId);
        mapper.insert(test);

        Map<String, String> result = new HashMap<>();
        result.put("error_message", "success");

        return result;
    }
}
