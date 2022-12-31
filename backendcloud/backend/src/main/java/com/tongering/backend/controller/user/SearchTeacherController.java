package com.tongering.backend.controller.user;

import com.tongering.backend.pojo.TeacherStudentRelationship;
import com.tongering.backend.pojo.UserAndUserProfile;
import com.tongering.backend.service.SearchUserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchTeacherController {
    @Autowired
    private SearchUserListService service;
    @PostMapping("/api/search/teacher/")
    public List<UserAndUserProfile> getList(@RequestParam Map<String, String> data){
        String type = "teacher";
        String name = data.get("name");
        return service.getSearch(name, type);
    }
}
