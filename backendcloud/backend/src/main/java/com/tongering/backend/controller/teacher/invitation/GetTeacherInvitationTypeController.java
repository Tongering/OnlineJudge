package com.tongering.backend.controller.teacher.invitation;

import com.tongering.backend.pojo.InvitationType;
import com.tongering.backend.service.invitation.GetInvitationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetTeacherInvitationTypeController {
    @Autowired
    private GetInvitationTypeService getInvitationTypeService;
    @PostMapping("/api/teacher/invitation/type/get/")
    public List<InvitationType> get(){
        return getInvitationTypeService.gettype();
    }
}
