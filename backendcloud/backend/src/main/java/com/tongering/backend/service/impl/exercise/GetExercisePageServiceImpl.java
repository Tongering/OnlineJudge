package com.tongering.backend.service.impl.exercise;

import com.tongering.backend.mapper.ExerciseMapper;
import com.tongering.backend.mapper.ExerciseTypeMapper;
import com.tongering.backend.pojo.Exercise;
import com.tongering.backend.pojo.ExercisesType;
import com.tongering.backend.service.exercise.GetExercisePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetExercisePageServiceImpl implements GetExercisePageService {
    @Autowired
    private ExerciseMapper mapper;
    @Autowired
    private ExerciseTypeMapper typeMapper;

    @Override
    public Map<String, String> getpage(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();

        Integer id = Integer.parseInt(data.get("id"));
        Exercise exercise = mapper.selectById(id);

        map.put("error_message", "success");
        map.put("id", exercise.getId().toString());
        map.put("name", exercise.getName());
        map.put("content", exercise.getContent());
        map.put("space", exercise.getSpace().toString());
        map.put("time", exercise.getTime().toString());

        ExercisesType type = typeMapper.selectById(exercise.getTypeId());
        map.put("type", type.getTypename());

        return map;
    }
}
