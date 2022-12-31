package com.tongering.backend.controller.user.exercise;

import com.tongering.backend.service.exercise.GetExercisePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetExercisePageController {
    @Autowired
    private GetExercisePageService service;

    @PostMapping("/api/exercise/page/")
    public Map<String, String> getpage (@RequestParam Map<String, String> data){
        return service.getpage(data);
    }

}
