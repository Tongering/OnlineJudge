package com.tongering.backend.controller.teacher.invitation;

import com.tongering.backend.service.invitation.AddInvitationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddInvitationTypeController {
    @Autowired
    private AddInvitationTypeService addInvitationTypeService;
    @PostMapping("/api/teacher/invitation/type/add/")
    private Map<String ,String > add(@RequestParam Map<String ,String> data){
        return addInvitationTypeService.add(data.get("typename"));
    }
}
