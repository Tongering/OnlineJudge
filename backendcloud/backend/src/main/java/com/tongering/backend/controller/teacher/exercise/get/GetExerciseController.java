package com.tongering.backend.controller.teacher.exercise.get;

import com.tongering.backend.pojo.Exercise;
import com.tongering.backend.service.exercise.GetExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetExerciseController {
    @Autowired
    private GetExerciseService service;

    @PostMapping("/api/exercise/get/")
    public List<Exercise> getexercise(@RequestParam Map<String, String> data){
        return service.getexercise(data);
    }
}
