package com.tongering.backend.service.impl.exercise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.CodeRecordMapper;
import com.tongering.backend.mapper.ExerciseMapper;
import com.tongering.backend.pojo.*;
import com.tongering.backend.service.exercise.GetRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetRecordServiceImpl implements GetRecordService {
    @Autowired
    private CodeRecordMapper recordMapper;
    @Autowired
    private ExerciseMapper exerciseMapper;
    @Override
    public List<SingleCodeRecord> getRecord(Integer userId ) {
        QueryWrapper<CodeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("record_time");
        List<CodeRecord> records = recordMapper.selectList(queryWrapper);
        List<SingleCodeRecord> singleCodeRecords = new ArrayList<>();
        for(CodeRecord i : records){
            Exercise exercise = exerciseMapper.selectById(i.getExerciseId());
            SingleCodeRecord singleCodeRecord = new SingleCodeRecord(i.getId(), i.getUserId(),
                    i.getExerciseId(), i.getResult(), i.getRecordTime(), exercise.getName(), exercise.getTypeId());
            singleCodeRecords.add(singleCodeRecord);
        }
        return singleCodeRecords;
    }
}
