package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.CountPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CountPeopleController {
    @Autowired
    private CountPeopleService service;
    @PostMapping("/api/user/count/get/")
    public Map<String, String> getCount(){
        return service.getCount();
    }
}
