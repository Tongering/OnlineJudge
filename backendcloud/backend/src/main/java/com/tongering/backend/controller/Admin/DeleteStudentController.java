package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.DeleteStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteStudentController {
    @Autowired
    private DeleteStudentService service;
    @PostMapping("/api/admin/student/delete/")
    public Map<String, String> delete(@RequestParam Map<String, String> data){
        return service.delete(Integer.parseInt(data.get("studentId")));
    }
}
