package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.ExerciseMapper;
import com.tongering.backend.pojo.Exercise;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.exercise.GetExerciseService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetExerciseServiceImpl implements GetExerciseService {
    @Autowired
    private ExerciseMapper mapper;
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;

    @Override
    public List<Exercise> getexercise(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();

        Map<String, String> isadmin = checkIsTeacherService.check();
        Map<String, String> map = new HashMap<>();

        if(user == null){
            return  null;
        }

        Integer type_id = Integer.parseInt(data.get("type_id"));
        QueryWrapper<Exercise> queryWrapper = new QueryWrapper<>();
        String onlyPersonal = "none";
        try{
            onlyPersonal = data.get("onlyPersonal");
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if("yes".equals(onlyPersonal)) {
                queryWrapper.eq("type_id", type_id).eq("create_id", user.getId());
            } else {
                queryWrapper.eq("type_id", type_id);

            }
            return mapper.selectList(queryWrapper);
        }

    }
}
