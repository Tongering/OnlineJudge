package com.tongering.backend.controller.user.invitation;

import com.tongering.backend.pojo.InvitationAndType;
import com.tongering.backend.service.invitation.GetPersonalInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetPsersonalInvitationController {
    @Autowired
    private GetPersonalInvitationService service;
    @PostMapping("/api/invitation/type/get/")
    public List<InvitationAndType> getInTy(){
        return service.getpersonal();
    }
}
