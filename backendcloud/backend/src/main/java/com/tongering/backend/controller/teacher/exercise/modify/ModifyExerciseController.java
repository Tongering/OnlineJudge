package com.tongering.backend.controller.teacher.exercise.modify;

import com.tongering.backend.service.exercise.ModifyExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyExerciseController {
    @Autowired
    private ModifyExerciseService service;

    @PostMapping("/api/exercise/modify/")
    public Map<String, String> modify (@RequestParam Map<String ,String>data){
        return service.modify(data);
    }
}
