package com.tongering.backend.controller.teacher.exercise.get;

import com.tongering.backend.pojo.ExercisesType;
import com.tongering.backend.service.exercise.GetExercisesTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetExerciseTypeController {
    @Autowired
    private GetExercisesTypeService typeService;

    @PostMapping("/api/teacher/exercise/type/get/")
    public List<ExercisesType> get(){
        return typeService.gettype();
    }
}
