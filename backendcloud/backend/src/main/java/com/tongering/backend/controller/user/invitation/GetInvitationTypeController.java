package com.tongering.backend.controller.user.invitation;

import com.tongering.backend.pojo.InvitationType;
import com.tongering.backend.service.invitation.GetInvitationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class GetInvitationTypeController {
    @Autowired
    private GetInvitationTypeService service;
    @GetMapping ("/api/invitation/type/")
    public List<InvitationType> gettype(){
        return service.gettype();
    }
}
