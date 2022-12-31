package com.tongering.backend.controller.Admin;

import com.tongering.backend.pojo.CommentAndUser;
import com.tongering.backend.service.admin.GetCommentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCommentListController {
    @Autowired
    private GetCommentListService service;
    @PostMapping("/api/admin/comment/get/")
    public List<CommentAndUser> get(){
        return service.getlist();
    }
}
