package com.tongering.backend.controller.teacher.check;

import com.tongering.backend.service.CheckIsTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CheckIsTeacherController {
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;

    @PostMapping("/api/teacher/check/teacher/")
    public Map<String, String> check() {
        return checkIsTeacherService.check();
    }
}
