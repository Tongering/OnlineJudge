package com.tongering.backend.controller.user.comment;

import com.tongering.backend.service.comment.DeleteCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteCommentController {
    @Autowired
    private DeleteCommentService deleteCommentService;
    @PostMapping("/api/comment/delete/")
    public Map<String, String> delete (@RequestParam Map<String, String> data){
        return deleteCommentService.delete(Integer.parseInt(data.get("id")), Integer.parseInt(data.get("invitation_user_id")));
    }
}
