package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.ExerciseMapper;
import com.tongering.backend.pojo.Exercise;
import com.tongering.backend.service.exercise.SearchExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchExerciseServiceImpl implements SearchExerciseService {
    @Autowired
    private ExerciseMapper exerciseMapper;

    @Override
    public List<Exercise> search(String searchName) {
        QueryWrapper<Exercise> exerciseQueryWrapper = new QueryWrapper<>();
        exerciseQueryWrapper.like("name", searchName);
        System.out.println(searchName);
        return exerciseMapper.selectList(exerciseQueryWrapper);
    }
}
