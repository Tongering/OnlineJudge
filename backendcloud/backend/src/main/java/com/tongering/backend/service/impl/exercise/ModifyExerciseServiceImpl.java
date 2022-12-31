package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.mapper.ExerciseMapper;
import com.tongering.backend.pojo.Exercise;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.exercise.ModifyExerciseService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class ModifyExerciseServiceImpl implements ModifyExerciseService {
    @Autowired
    private ExerciseMapper mapper;
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;

    @Override
    public Map<String, String> modify(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();

        Map<String, String> isTeacher = checkIsTeacherService.check();
        Map<String, String> map = new HashMap<>();

        if(isTeacher.get("error_message").equals("admin_false")){
            return isTeacher;
        }

        Integer id = Integer.parseInt(data.get("id"));
        ;
        String name = data.get("name");
        String content = data.get("content");

        Integer typeId = Integer.parseInt(data.get("typeId"));
        Integer space = Integer.parseInt(data.get("space"));
        Double time = Double.parseDouble(data.get("time"));
        String template = data.get("template");

        UpdateWrapper<Exercise> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).eq("create_id", user.getId())
                .set("name", name).set("content", content).set("type_id", typeId).set("space", space)
                .set("time", time).set("template", template);
        mapper.update(null, updateWrapper);
        map.put("error_message", "success");
        return map;
    }
}
