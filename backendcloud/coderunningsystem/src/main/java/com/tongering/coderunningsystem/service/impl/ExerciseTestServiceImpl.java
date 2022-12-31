package com.tongering.coderunningsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.coderunningsystem.mapper.ExerciseTestMapper;
import com.tongering.coderunningsystem.pojo.ExerciseTest;
import com.tongering.coderunningsystem.service.ExerciseTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExerciseTestServiceImpl implements ExerciseTestService {

    @Autowired
    private ExerciseTestMapper mapper;
    @Override
    public List<ExerciseTest> gettest(Integer exerciseId) {

        QueryWrapper<ExerciseTest> wrapper = new QueryWrapper<>();
        wrapper.eq("exercise_id", exerciseId);
        List<ExerciseTest> listraw = mapper.selectList(wrapper);
        return listraw;
    }
}
