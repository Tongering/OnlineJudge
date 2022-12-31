package com.tongering.backend.controller.user.invitation;

import com.tongering.backend.pojo.MultiInvitationBrief;
import com.tongering.backend.service.invitation.GetInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetInvitationController {
    @Autowired
    private GetInvitationService service;

    @PostMapping("/api/invitation/getpage/")
    public MultiInvitationBrief getInvitation(@RequestParam Map<String, String> data){
        Integer pageId = Integer.parseInt(data.get("pageId"));
        return service.getinvitation(pageId);
    }
}
