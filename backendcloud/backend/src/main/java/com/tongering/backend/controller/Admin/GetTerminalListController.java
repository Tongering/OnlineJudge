package com.tongering.backend.controller.Admin;

import com.tongering.backend.pojo.TerminalAndUserAndProfile;
import com.tongering.backend.service.admin.GetTerminalListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetTerminalListController {
    @Autowired
    private GetTerminalListService service;
    @PostMapping("/api/admin/terminal/get/")
    public List<TerminalAndUserAndProfile> get(){
        return service.getList();
    }
}
