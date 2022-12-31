package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.ExerciseTestMapper;
import com.tongering.backend.pojo.ExerciseTest;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.CheckUserService;
import com.tongering.backend.service.exercise.DeleteExerciseTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteExerciseTestServiceImpl implements DeleteExerciseTestService {
    @Autowired
    private ExerciseTestMapper exerciseTestMapper;
    @Autowired
    private CheckUserService checkUserService;
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;
    @Override
    public Map<String, String> delete(Integer id) {
        Map<String, String> result = new HashMap<>();
        if(checkIsTeacherService.check().get("error_message").equals("teacher_true")){
            QueryWrapper<ExerciseTest> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id).eq("create_id", checkUserService.check().getId());
            try {
                exerciseTestMapper.delete(queryWrapper);
                result.put("error_message", "success");
                return result;
            } catch (Exception e){
                result.put("error_message", "error");
                return result;
            }
        }
        result.put("error_message", "error");
        return result;
    }
}
