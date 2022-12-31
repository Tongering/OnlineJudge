package com.tongering.coderunningsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.coderunningsystem.mapper.ExerciseMapper;
import com.tongering.coderunningsystem.mapper.ExerciseTestMapper;
import com.tongering.coderunningsystem.pojo.ExerciseTest;
import com.tongering.coderunningsystem.service.CodeRunningService;
import com.tongering.coderunningsystem.service.ExerciseTestService;
import com.tongering.coderunningsystem.service.impl.util.CodePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeRunningServiceImpl implements CodeRunningService {
    public final static CodePool codepool = new CodePool();

    @Autowired
    private ExerciseTestMapper mapper;

    @Autowired
    private ExerciseMapper exerciseMapper;
    @Override
    public String addCode(Integer userId, String code, Integer exerciseId) {
        System.out.println("addcodeing..");
        QueryWrapper<ExerciseTest> wrapper = new QueryWrapper<>();
        wrapper.eq("exercise_id", exerciseId);
        List<ExerciseTest> listraw = mapper.selectList(wrapper);

        Double time = exerciseMapper.selectById(exerciseId).getTime();

        codepool.addCode(userId, code, exerciseId, listraw, time);
        return "add code successful";
    }
}
