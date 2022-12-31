package com.tongering.backend.controller.Admin;

import com.tongering.backend.pojo.ProfileAndUser;
import com.tongering.backend.service.admin.GetStudentProfileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetStudentProfileListController {
    @Autowired
    private GetStudentProfileListService service;
    @PostMapping("/api/admin/student/get/")
    public List<ProfileAndUser> get(){
        return service.getList();
    }
}
