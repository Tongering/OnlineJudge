package com.tongering.backend.controller.search;

import com.tongering.backend.pojo.Exercise;
import com.tongering.backend.service.exercise.SearchExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchExerciseController {
    @Autowired
    private SearchExerciseService searchExerciseService;
    @PostMapping("/api/exercise/search/")
    public List<Exercise> search(@RequestParam Map<String, String> data){
        return  searchExerciseService.search(data.get("searchName"));
    }
}
