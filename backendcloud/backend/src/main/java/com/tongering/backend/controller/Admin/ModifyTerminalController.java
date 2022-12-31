package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.ModifyTerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyTerminalController {
    @Autowired
    private ModifyTerminalService service;
    @PostMapping("/api/admin/terminal/modify/")
    public Map<String, String> modify(@RequestParam Map<String, String> data){
        return service.modify(data);
    }
}
