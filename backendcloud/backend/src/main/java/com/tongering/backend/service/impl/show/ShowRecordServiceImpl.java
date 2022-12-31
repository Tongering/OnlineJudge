package com.tongering.backend.service.impl.show;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.*;
import com.tongering.backend.pojo.*;
import com.tongering.backend.service.show.ShowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowRecordServiceImpl implements ShowRecordService {
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private ExerciseMapper exerciseMapper;
    @Autowired
    private ExerciseTypeMapper exerciseTypeMapper;
    @Autowired
    private CodeRecordMapper codeRecordMapper;

    @Override
    public List<ShowRecord> getShow() {
        List<ShowRecord> result = new ArrayList<>();
        QueryWrapper<CodeRecord> codeRecordQueryWrapper = new QueryWrapper<>();
        codeRecordQueryWrapper.orderByDesc("record_time").last("limit 10");
        List<CodeRecord> codeRecords = codeRecordMapper.selectList(codeRecordQueryWrapper);
        List<ExercisesType> exercisesTypes = exerciseTypeMapper.selectList(null);
        for(CodeRecord i : codeRecords){
            String photo = userProfileMapper.selectById(i.getUserId()).getPhoto();
            String name = usersMapper.selectById(i.getUserId()).getUsername();
            Exercise exercise = exerciseMapper.selectById(i.getExerciseId());
            String codeName = exercise.getName();
            Integer typeId = exercise.getTypeId();
            String typeName = null;
            for(ExercisesType j : exercisesTypes){
                if(typeId.equals(j.getId())){
                    typeName = j.getTypename();
                    break;
                }
            }
            ShowRecord showRecord = new ShowRecord(i.getUserId(), photo, name, i.getExerciseId(),
                    codeName, typeName,i.getId(), i.getResult(), i.getRecordTime());
            result.add(showRecord);
        }  


        return result;
    }
}
