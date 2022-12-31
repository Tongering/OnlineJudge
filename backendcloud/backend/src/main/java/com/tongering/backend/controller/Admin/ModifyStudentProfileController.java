package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.ModifyStudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyStudentProfileController {
    @Autowired
    private ModifyStudentProfileService service;
    @PostMapping("/api/admin/student/modify/")
    public Map<String, String> modify(@RequestParam Map<String, String> data){
        return service.modify(data);
    }
}
