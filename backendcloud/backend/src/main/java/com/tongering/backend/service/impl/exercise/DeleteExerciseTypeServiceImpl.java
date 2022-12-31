package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.ExerciseTypeMapper;
import com.tongering.backend.pojo.ExercisesType;
import com.tongering.backend.service.CheckUserService;
import com.tongering.backend.service.exercise.DeleteExerciseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteExerciseTypeServiceImpl implements DeleteExerciseTypeService {
    @Autowired
    private ExerciseTypeMapper typeMapper;
    @Autowired
    private CheckUserService checkUserService;

    @Override
    public Map<String, String> delete(Integer id) {
        Map<String, String> result = new HashMap<>();

        QueryWrapper<ExercisesType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).eq("create_id", checkUserService.check().getId());

        try {
            typeMapper.delete(queryWrapper);
            result.put("error_message", "success");
            return result;
        } catch (Exception e){
            result.put("error_message", "error");
            return result;
        }

    }
}
