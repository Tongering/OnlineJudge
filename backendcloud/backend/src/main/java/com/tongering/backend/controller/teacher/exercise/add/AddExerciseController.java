package com.tongering.backend.controller.teacher.exercise.add;

import com.tongering.backend.service.exercise.AddExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddExerciseController {
    @Autowired
    private AddExerciseService addExerciseService;

    @PostMapping("/api/exercise/add/")
    public Map<String, String> add (@RequestParam Map<String, String> data){
        return addExerciseService.addexercise(data);
    }
}
