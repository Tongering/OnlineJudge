package com.tongering.backend.controller.Admin;

import com.tongering.backend.pojo.ProfileAndUser;
import com.tongering.backend.service.admin.GetTeacherProfileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetTeacherProfileListController {
    @Autowired
    private GetTeacherProfileListService service;
    @PostMapping("/api/admin/teacher/get/")
    public List<ProfileAndUser> getList(){
        return service.getList();
    }
}
