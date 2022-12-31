package com.tongering.backend.service.impl.invitation;

import com.tongering.backend.mapper.InvitationMapper;
import com.tongering.backend.pojo.Invitation;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import com.tongering.backend.service.invitation.AddInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddInvitationServiceImpl implements AddInvitationService {
    @Autowired
    private InvitationMapper mapper;
    @Override
    public Map<String, String> add(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();

        Map<String, String> result = new HashMap<>();

        String title = data.get("title");
        Integer typeId = Integer.parseInt(data.get("typeId"));
        String content = data.get("content");
        Integer userId = user.getId();

        Invitation invitation = new Invitation(null, title, typeId, content, userId, new Date());
        try{
            mapper.insert(invitation);
            result.put("error_message", "success");
        } catch (Exception e){
            e.printStackTrace();
            result.put("error_message", "error");
        }

        return result;
    }
}
