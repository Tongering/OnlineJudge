package com.tongering.backend.controller.user;

import com.tongering.backend.service.GetUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetUserProfileController {
    @Autowired
    private GetUserProfileService service;
    @PostMapping("/api/profile/get/")
    public Map<String, String> getInfo(){
        return service.getProfile();
    }
}
