package com.tongering.backend.controller.teacher.student;

import com.tongering.backend.pojo.SingleCodeRecord;
import com.tongering.backend.service.exercise.GetRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetStudentRecordController {
    @Autowired
    GetRecordService service;
    @PostMapping("/api/teacher/student/get/record/")
    public List<SingleCodeRecord> getRecord (@RequestParam Map<String, String> data){
        return service.getRecord(Integer.parseInt(data.get("userId")));
    }
}
