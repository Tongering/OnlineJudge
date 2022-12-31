package com.tongering.backend.controller.teacher.exercise.modify;

import com.tongering.backend.service.exercise.ModifyExerciseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyExerciseTypeController {
    @Autowired
    private ModifyExerciseTypeService modifyExerciseTypeService;
    @PostMapping("/api/teacher/exercise/type/modify/")
    public Map<String, String> modify(@RequestParam Map<String, String> data) {
        return modifyExerciseTypeService.modify(data.get("typename"), Integer.parseInt(data.get("id")));
    }
}
