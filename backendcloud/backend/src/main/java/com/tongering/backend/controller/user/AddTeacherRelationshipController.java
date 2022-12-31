package com.tongering.backend.controller.user;

import com.tongering.backend.service.AddTeacherRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddTeacherRelationshipController {
    @Autowired
    private AddTeacherRelationshipService service;
    @PostMapping("/api/add/teacher/")
    public Map<String, String> add(@RequestParam Map<String, String> data){
        return service.add(Integer.parseInt(data.get("id")));
    }
}
