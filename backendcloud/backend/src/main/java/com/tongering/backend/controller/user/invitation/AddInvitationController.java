package com.tongering.backend.controller.user.invitation;

import com.tongering.backend.service.invitation.AddInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddInvitationController {
    @Autowired
    private AddInvitationService service;

    @PostMapping("/api/invitation/add/")
    public Map<String, String> add (@RequestParam Map<String, String> data){
        return service.add(data);
    }
}
