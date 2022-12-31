package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.AddAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddAdminController {
    @Autowired
    private AddAdminService service;
    @PostMapping("/api/admin/admin/add/")
    public Map<String, String> add(@RequestParam Map<String, String>data){
        return service.add(Integer.parseInt(data.get("userId")));
    }
}
