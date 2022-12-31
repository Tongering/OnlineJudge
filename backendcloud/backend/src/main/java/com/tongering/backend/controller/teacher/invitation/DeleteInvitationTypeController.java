package com.tongering.backend.controller.teacher.invitation;

import com.tongering.backend.service.invitation.DeleteInvitationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteInvitationTypeController {
    @Autowired
    private DeleteInvitationTypeService deleteInvitationTypeService;
    @PostMapping("/api/teacher/invitation/type/delete/")
    public Map<String, String> delete (@RequestParam Map<String, String> data){
        return deleteInvitationTypeService.delete(Integer.parseInt(data.get("id")));
    }
}
