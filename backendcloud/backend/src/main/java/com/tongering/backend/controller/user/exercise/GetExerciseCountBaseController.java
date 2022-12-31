package com.tongering.backend.controller.user.exercise;

import com.tongering.backend.service.exercise.GetExerciseCountBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetExerciseCountBaseController {
    @Autowired
    private GetExerciseCountBaseService service;
    @PostMapping("/api/exercise/count/")
    public Map<String, Integer> getCount(){
        return service.getCount();
    }
}
