package com.tongering.backend.controller.user.exercise;

import com.tongering.backend.pojo.ExercisesType;
import com.tongering.backend.service.exercise.GetExercisesTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetExercisesTypeController {
    @Autowired
    private GetExercisesTypeService getExercisesTypeService;

    @GetMapping("/api/exercises/type/")
    public List<ExercisesType> gettype(){
        return getExercisesTypeService.gettype();
    }
}
