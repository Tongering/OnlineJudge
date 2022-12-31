package com.tongering.backend.controller.teacher.exercise.delete;

import com.tongering.backend.service.exercise.DeleteExerciseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteExerciseTypeController {
    @Autowired
    private DeleteExerciseTypeService deleteExerciseTypeService;
    @PostMapping("/api/teacher/exercise/type/delete/")
    public Map<String, String> delete(@RequestParam Map<String, String> data){
        return deleteExerciseTypeService.delete(Integer.parseInt(data.get("id")));
    }
}
