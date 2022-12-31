package com.tongering.backend.service.impl.comment;

import com.tongering.backend.mapper.CommentMapper;
import com.tongering.backend.pojo.Comment;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.comment.AddCommentService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddCommentServiceImpl implements AddCommentService {
    @Autowired
    private CommentMapper mapper;
    @Override
    public Map<String, String> addComment(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        Integer invitationId = Integer.parseInt(data.get("invitationId"));
        String content = data.get("content");
        Comment comment = new Comment(null, user.getId(), invitationId, content, new Date());
        Map<String, String>result = new HashMap<>();
        try{
            mapper.insert(comment);
            result.put("error_message", "success");
        } catch (Exception e){
            result.put("error_message", "error");
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
