package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.AddBarPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddBarPhotoController {
    @Autowired
    private AddBarPhotoService service;
    @PostMapping("/api/admin/bar/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data){
        return service.add(data);
    }
}
