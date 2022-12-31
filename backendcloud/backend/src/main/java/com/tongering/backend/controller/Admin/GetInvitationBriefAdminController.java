package com.tongering.backend.controller.Admin;

import com.tongering.backend.pojo.MultiInvitationBrief;
import com.tongering.backend.service.invitation.GetInvitationBriefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetInvitationBriefAdminController {
    @Autowired
    private GetInvitationBriefService service;
    @PostMapping("/api/admin/invitation/get/")
    public List<MultiInvitationBrief> get(@RequestParam Map<String, String>data){
        return service.getBrief(data);
    }
}
