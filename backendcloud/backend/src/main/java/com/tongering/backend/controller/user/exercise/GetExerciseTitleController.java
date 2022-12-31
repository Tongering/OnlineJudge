package com.tongering.backend.controller.user.exercise;


import com.tongering.backend.mapper.ExerciseTypeMapper;

import com.tongering.backend.pojo.ExercisesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetExerciseTitleController {
    @Autowired
    private ExerciseTypeMapper mapper;

    @GetMapping("/api/exercise/title/get/")
    public ExercisesType gettitle(@RequestParam Map<String, String>data){
        Integer typeId = Integer.parseInt(data.get("typeId"));
        return mapper.selectById(typeId);
    }

}
