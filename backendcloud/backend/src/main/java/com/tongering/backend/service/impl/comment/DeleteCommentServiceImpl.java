package com.tongering.backend.service.impl.comment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.CommentMapper;
import com.tongering.backend.pojo.Comment;
import com.tongering.backend.service.CheckUserService;
import com.tongering.backend.service.comment.DeleteCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteCommentServiceImpl implements DeleteCommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CheckUserService checkUserService;
    @Override
    public Map<String, String> delete(Integer id, Integer invitationUserId) {
        Map<String, String> result = new HashMap<>();
        Comment comment = commentMapper.selectById(id);
        if(comment.getUserId().equals(checkUserService.check().getId()) || comment.getId().equals(invitationUserId)){
            try{
                commentMapper.deleteById(id);
                result.put("error_message", "success");
                return result;
            } catch (Exception e){
                result.put("error_message", "error");
                return result;
            }
        }

        result.put("error_message", "error");
        return result;
    }
}
