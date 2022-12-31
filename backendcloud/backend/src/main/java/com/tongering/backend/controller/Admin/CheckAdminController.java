package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.CheckAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CheckAdminController {
    @Autowired
    private CheckAdminService service;
    @PostMapping("/api/admin/check/admin/")
    private Map<String, String> check(){
        return service.check();
    }
}
