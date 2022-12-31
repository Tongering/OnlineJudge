package com.tongering.backend.controller.user.exercise;

import com.tongering.backend.mapper.ExerciseMapper;
import com.tongering.backend.pojo.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetExerciseTemplateOneController {
    @Autowired
    private ExerciseMapper mapper;

    @PostMapping("/api/exercise/template/get/")
    public String gettemplate(@RequestParam Map<String, String>data){
        Integer exercisePage = Integer.parseInt(data.get("type_id"));
        Exercise exercise = mapper.selectById(exercisePage);
        return exercise.getTemplate();
    }

}
