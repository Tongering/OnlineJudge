package com.tongering.backend.controller.user.invitation;

import com.tongering.backend.service.invitation.UpdateInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateInvitationController {
    @Autowired
    private UpdateInvitationService service;
    @PostMapping("/api/invitation/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data){
        return service.update(data);
    }
}
