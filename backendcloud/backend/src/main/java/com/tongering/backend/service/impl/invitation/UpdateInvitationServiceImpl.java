package com.tongering.backend.service.impl.invitation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.mapper.InvitationMapper;
import com.tongering.backend.pojo.Invitation;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import com.tongering.backend.service.invitation.UpdateInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateInvitationServiceImpl implements UpdateInvitationService {
    @Autowired
    private InvitationMapper mapper;
    @Override
    public Map<String, String> update(Map<String, String> data) {
        Map<String, String> result = new HashMap<>();

        Integer invitationId = Integer.parseInt(data.get("invitationId"));
        String invitationTitle = data.get("invitationTitle");
        Integer invitationType = Integer.parseInt(data.get("invitationType"));
        String invitationContent = data.get("invitationContent");

        try {
            UpdateWrapper<Invitation> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", invitationId)
                    .set("title", invitationTitle)
                    .set("type_id", invitationType)
                    .set("content", invitationContent);
            mapper.update(null, updateWrapper);
            result.put("error_message", "success");
        } catch (Exception e){
            e.printStackTrace();
            result.put("error_message", "error");
        }
        return result;
    }
}
