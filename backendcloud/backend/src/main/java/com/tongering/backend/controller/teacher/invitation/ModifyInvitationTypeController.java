package com.tongering.backend.controller.teacher.invitation;

import com.tongering.backend.service.invitation.ModifyInvitationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyInvitationTypeController {
    @Autowired
    private ModifyInvitationTypeService modifyInvitationTypeService;
    @PostMapping("/api/teacher/invitation/type/modify/")
    public Map<String, String> modify(@RequestParam Map<String, String> data ){
        return modifyInvitationTypeService.modify(data.get("typename"), Integer.parseInt(data.get("id")));
    }
}
