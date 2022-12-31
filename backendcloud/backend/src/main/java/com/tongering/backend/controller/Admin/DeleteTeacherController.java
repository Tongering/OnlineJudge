package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.DeleteTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteTeacherController {
    @Autowired
    private DeleteTeacherService service;
    @PostMapping("/api/admin/teacher/delete/")
    public Map<String, String> delete(@RequestParam Map<String, String> data){
        return service.delete(Integer.parseInt(data.get("teacherId")));
    }
}
