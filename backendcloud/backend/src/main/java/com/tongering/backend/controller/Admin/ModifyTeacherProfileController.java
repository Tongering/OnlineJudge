package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.ModifyTeacherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyTeacherProfileController {
    @Autowired
    private ModifyTeacherProfileService service;
    @PostMapping("/api/admin/teacher/modify/")
    public Map<String, String> modify(@RequestParam Map<String, String> data){
        return service.modify(data);
    }
}
