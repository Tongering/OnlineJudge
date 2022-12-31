package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.DeleteTerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteTerminalController {
    @Autowired
    private DeleteTerminalService service;
    @PostMapping("/api/admin/terminal/delete/")
    public Map<String, String> delete(@RequestParam Map<String, String> data){
        return service.delete(Integer.parseInt(data.get("userId")));
    }
}
