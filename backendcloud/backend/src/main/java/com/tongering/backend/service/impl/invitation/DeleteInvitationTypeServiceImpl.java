package com.tongering.backend.service.impl.invitation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.CommentMapper;
import com.tongering.backend.mapper.InvitationMapper;
import com.tongering.backend.mapper.InvitationTypeMapper;
import com.tongering.backend.pojo.Comment;
import com.tongering.backend.pojo.Invitation;
import com.tongering.backend.pojo.InvitationType;
import com.tongering.backend.service.CheckUserService;
import com.tongering.backend.service.invitation.DeleteInvitationService;
import com.tongering.backend.service.invitation.DeleteInvitationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeleteInvitationTypeServiceImpl implements DeleteInvitationTypeService {
    @Autowired
    private InvitationTypeMapper invitationTypeMapper;
    @Autowired
    private CheckUserService checkUserService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private InvitationMapper invitationMapper;
    @Override
    public Map<String, String> delete(Integer id) {
        Map<String, String> result = new HashMap<>();
        try {
            QueryWrapper<InvitationType> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id).eq("create_id", checkUserService.check().getId());
            QueryWrapper<Invitation> invitationQueryWrapper = new QueryWrapper<>();
            invitationQueryWrapper.eq("type_id", id);
            List<Invitation> invitations = invitationMapper.selectList(invitationQueryWrapper);
            for(Invitation i : invitations){
                invitationMapper.deleteById(i.getId());
                QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
                queryWrapper.eq("invitation_id", i.getId());
                commentMapper.delete(commentQueryWrapper);
            }
            invitationTypeMapper.delete(queryWrapper);
            result.put("error_message", "success");
            return result;
        } catch (Exception e){
            result.put("error_message", "error");
            return result;
        }
    }
}
