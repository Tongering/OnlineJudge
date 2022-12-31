package com.tongering.backend.controller.user.comment;

import com.tongering.backend.pojo.Comment;
import com.tongering.backend.pojo.MultiComment;
import com.tongering.backend.service.comment.GetCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetCommentController {
    @Autowired
    private GetCommentService service;
    @PostMapping("/api/comment/get/")
    private List<MultiComment> getComment(@RequestParam Map<String, String>data){
        Integer invitationId = Integer.parseInt(data.get("invitationId"));
        return service.getComment(invitationId);
    }
}
