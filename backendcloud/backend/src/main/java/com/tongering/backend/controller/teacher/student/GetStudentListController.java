package com.tongering.backend.controller.teacher.student;

import com.tongering.backend.pojo.TeacherStudentRelationship;
import com.tongering.backend.pojo.UserAndUserProfile;
import com.tongering.backend.service.GetStudentListService;
import com.tongering.backend.service.impl.GetStudentListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetStudentListController {
    @Autowired
    private GetStudentListService service;
    @PostMapping("/api/teacher/student/get/list/")
    public List<UserAndUserProfile> getlist(){
        return service.getList();
    }
}
