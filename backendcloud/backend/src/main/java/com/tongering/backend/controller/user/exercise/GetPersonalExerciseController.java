package com.tongering.backend.controller.user.exercise;

import com.tongering.backend.pojo.SingleCodeRecord;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.exercise.GetRecordService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetPersonalExerciseController {
    @Autowired
    private GetRecordService service;
    @PostMapping("/api/record/get/")
    public List<SingleCodeRecord> getRecord(){
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        return service.getRecord(user.getId());
    }
}
