package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.ExerciseTestMapper;
import com.tongering.backend.pojo.ExerciseTest;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.exercise.GetExerciseTestService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetExerciseTestServiceImpl implements GetExerciseTestService {
    @Autowired
    private ExerciseTestMapper testMapper;

    @Override
    public List<ExerciseTest> gettest() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        QueryWrapper<ExerciseTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_id", user.getId());

        return testMapper.selectList(queryWrapper);
    }
}
