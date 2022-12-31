package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.AddTerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddTerminalController {
    @Autowired
    private AddTerminalService service;
    @PostMapping("/api/admin/terminal/add/")
    public Map<String, String> add (@RequestParam Map<String, String> data){
        return service.add(data);
    }
}
