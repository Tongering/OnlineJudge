package com.tongering.backend.controller.teacher.exercise.delete;

import com.tongering.backend.service.exercise.DeleteExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteExerciseController {
    @Autowired
    private DeleteExerciseService exerciseService;
    @PostMapping("/api/teacher/exercise/delete/")
    public Map<String, String> delete(@RequestParam Map<String, String> data){
        return exerciseService.delete(Integer.parseInt(data.get("id")));
    }
}
