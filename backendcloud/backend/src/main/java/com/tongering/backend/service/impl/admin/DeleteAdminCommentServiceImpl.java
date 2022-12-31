package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.CommentMapper;
import com.tongering.backend.service.admin.DeleteAdminCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteAdminCommentServiceImpl implements DeleteAdminCommentService {
    @Autowired
    private CommentMapper mapper;

    @Override
    public Map<String, String> delete(Integer commentId) {
        Map<String, String> result = new HashMap<>();
        mapper.deleteById(commentId);
        result.put("error_message", "success");
        return result;
    }
}
