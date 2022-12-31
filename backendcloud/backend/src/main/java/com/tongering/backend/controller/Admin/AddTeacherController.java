package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.AddTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddTeacherController {
    @Autowired
    private AddTeacherService service;
    @PostMapping("/api/admin/user/teacher/")
    public Map<String, String> add(@RequestParam Map<String, String> data){
        return service.add(Integer.parseInt(data.get("userId")));
    }
}
