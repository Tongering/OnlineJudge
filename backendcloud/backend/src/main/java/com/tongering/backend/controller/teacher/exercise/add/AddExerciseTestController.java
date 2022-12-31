package com.tongering.backend.controller.teacher.exercise.add;

import com.tongering.backend.service.exercise.AddExerciseTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddExerciseTestController {
    @Autowired
    private AddExerciseTestService testService;

    @PostMapping("/api/exercise/test/add/")
    public Map<String, String> addtest (@RequestParam Map<String, String> data){
        return testService.addtest(data);
    }
}
