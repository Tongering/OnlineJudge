package com.tongering.backend.service.impl.exercise;

import com.tongering.backend.mapper.ExerciseMapper;
import com.tongering.backend.pojo.Exercise;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.exercise.AddExerciseService;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddExerciseServiceImpl implements AddExerciseService {
    @Autowired
    private ExerciseMapper mapper;

    @Autowired
    private CheckIsTeacherService checkIsTeacherService;

    @Override
    public Map<String, String> addexercise(Map<String, String> data) {

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();

        Map<String, String> isTeacher = checkIsTeacherService.check();
        Map<String, String> map = new HashMap<>();

        if(isTeacher.get("error_message").equals("admin_false")){
            return isTeacher;
        }
        System.out.println(data);
        String name = data.get("name");
        String content = data.get("content");
        Integer create_id = user.getId();
        Integer type_id = Integer.parseInt(data.get("type_id"));
        Date create_time = new Date();
        Integer space = Integer.parseInt(data.get("space"));
        Double time = Double.parseDouble(data.get("time"));
        String template = data.get("template");

        Exercise exercise = new Exercise(null, name, content, create_id, type_id, create_time, space, time, template);

        mapper.insert(exercise);

        map.put("error_message", "success");

        return map;
    }
}
