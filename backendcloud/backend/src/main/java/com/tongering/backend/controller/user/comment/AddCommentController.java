package com.tongering.backend.controller.user.comment;

import com.tongering.backend.service.comment.AddCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class AddCommentController {
    @Autowired
    private AddCommentService service;
    @PostMapping("/api/comment/add/")
    public Map<String, String> addComment(@RequestParam Map<String, String> data){
        return service.addComment(data);
    }
}
