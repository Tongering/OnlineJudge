package com.tongering.backend.controller.Admin;

import com.tongering.backend.pojo.ProfileAndUser;
import com.tongering.backend.service.admin.GetAdminListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetAdminListController {
    @Autowired
    private GetAdminListService service;
    @PostMapping("/api/admin/admin/get/")
    public List<ProfileAndUser> getList(){
        return service.getList();
    }
}
