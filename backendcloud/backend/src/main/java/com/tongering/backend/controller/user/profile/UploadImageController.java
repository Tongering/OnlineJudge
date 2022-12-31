package com.tongering.backend.controller.user.profile;

import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.image.UploadImageService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadImageController {
    @Autowired
    private UploadImageService service;
    @PostMapping("/api/image/person/invitation/upload/")
    public String upload(@RequestParam MultipartFile file){
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        return service.upload(file, user.getId());
    }
}
