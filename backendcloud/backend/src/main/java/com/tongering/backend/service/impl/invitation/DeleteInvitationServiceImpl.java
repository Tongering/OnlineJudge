package com.tongering.backend.service.impl.invitation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.AdminMapper;
import com.tongering.backend.mapper.CommentMapper;
import com.tongering.backend.mapper.InvitationMapper;
import com.tongering.backend.pojo.Admin;
import com.tongering.backend.pojo.Comment;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import com.tongering.backend.service.invitation.DeleteInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DeleteInvitationServiceImpl implements DeleteInvitationService {
    @Autowired
    private InvitationMapper mapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Map<String, String> delete(Integer invitationId) {
        Map<String, String> result = new HashMap<>();

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        List<Admin> admins = adminMapper.selectList(null);
        String isAdmin = "false";
        for( Admin i : admins){
            if(i.getId().equals(user.getId())){
                isAdmin = "true";
                break;
            }
        }
        Integer createId = mapper.selectById(invitationId).getCreateId();
        if(createId.equals(user.getId()) || isAdmin.equals("true")){
            result.put("error_message", "success");
            mapper.deleteById(invitationId);
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("invitation_id", invitationId);
            commentMapper.delete(queryWrapper);
        } else {
            result.put("error_message", "error");
        }
        return result;
    }
}
