package com.tongering.backend.controller.identify;

import com.tongering.backend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/api/user/register/")
    public Map<String, String> register (@RequestParam Map<String, String> data){
        return registerService.register(data);
    }
}
