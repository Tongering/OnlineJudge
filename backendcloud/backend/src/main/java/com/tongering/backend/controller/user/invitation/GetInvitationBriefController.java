package com.tongering.backend.controller.user.invitation;

import com.tongering.backend.pojo.Invitation;
import com.tongering.backend.pojo.MultiInvitationBrief;
import com.tongering.backend.service.invitation.GetInvitationBriefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetInvitationBriefController {
    @Autowired
    private GetInvitationBriefService service;

    @GetMapping("/api/invitation/brief/")
    private List<MultiInvitationBrief> getBrief(@RequestParam Map<String, String> data){
        return service.getBrief(data);
    }
}
