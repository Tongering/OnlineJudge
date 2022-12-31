package com.tongering.backend.controller.user.invitation;

import com.tongering.backend.service.invitation.DeleteInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteInvitationController {
    @Autowired
    private DeleteInvitationService service;
    @PostMapping("/api/invitation/delete/")
    public Map<String, String> delete (@RequestParam Map<String ,String> data){
        Integer invitationId = Integer.parseInt(data.get("invitationId"));
        return service.delete(invitationId);
    }
}
