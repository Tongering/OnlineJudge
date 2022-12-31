package com.tongering.backend.controller.codeservice.exercise;

import com.tongering.backend.pojo.ExerciseTest;
import com.tongering.backend.service.exercise.GetExerciseTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetExerciseTestController {
    @Autowired
    private GetExerciseTestService testService;

    @GetMapping("/api/exercise/test/get/")
    public List<ExerciseTest> gettest (){
        return testService.gettest();
    }
}
