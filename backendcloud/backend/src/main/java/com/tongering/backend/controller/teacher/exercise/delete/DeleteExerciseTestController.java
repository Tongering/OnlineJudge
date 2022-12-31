package com.tongering.backend.controller.teacher.exercise.delete;

import com.tongering.backend.service.exercise.DeleteExerciseTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteExerciseTestController {
    @Autowired
    private DeleteExerciseTestService exerciseTestService;
    @PostMapping("/api/teacher/exercise/test/delete/")
    public Map<String, String> delete(@RequestParam Map<String, String > data){
        return exerciseTestService.delete(Integer.parseInt(data.get("id")));
    }
}
