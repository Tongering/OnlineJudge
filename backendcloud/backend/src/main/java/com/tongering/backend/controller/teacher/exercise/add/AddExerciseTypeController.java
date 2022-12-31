package com.tongering.backend.controller.teacher.exercise.add;

import com.tongering.backend.service.exercise.AddExerciseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddExerciseTypeController {
    @Autowired
    private AddExerciseTypeService addExerciseTypeService;
    @PostMapping("/api/teacher/exercise/type/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
        return addExerciseTypeService.add(data.get("typename"));
    }
}
