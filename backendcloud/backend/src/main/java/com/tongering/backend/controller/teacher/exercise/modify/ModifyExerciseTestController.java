package com.tongering.backend.controller.teacher.exercise.modify;

import com.tongering.backend.service.exercise.ModifyExerciseTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyExerciseTestController {
    @Autowired
    private ModifyExerciseTestService testService;

    @PostMapping("/api/exercise/test/modify/")
    public Map<String, String> modifytest (@RequestParam Map<String, String> data){
        return testService.modifytest(data);
    }
}
