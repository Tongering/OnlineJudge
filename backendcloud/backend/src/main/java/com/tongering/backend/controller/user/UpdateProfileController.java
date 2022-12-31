package com.tongering.backend.controller.user;

import com.tongering.backend.service.UpdateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateProfileController {
    @Autowired
    private UpdateProfileService service;
    @PostMapping("/api/profile/improve/")
    public Map<String, String> update(@RequestParam Map<String, String> data){
        return service.update(data);
    }
}
