package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.invitation.UpdateInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyInvitationAdminController {
    @Autowired
    private UpdateInvitationService service;
    @PostMapping("/api/admin/invitation/modify/")
    public Map<String, String> modify(@RequestParam Map<String, String> data){
        return service.update(data);
    }
}
