package com.tongering.backend.controller.show;

import com.tongering.backend.pojo.ShowRecord;
import com.tongering.backend.service.show.ShowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShowRecordController {
    @Autowired
    public ShowRecordService service;
    @GetMapping("/api/show/record/")
    public List<ShowRecord> getshow(){
        return service.getShow();
    }
}
