package com.tongering.backend.service.impl.invitation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.InvitationMapper;
import com.tongering.backend.mapper.InvitationTypeMapper;
import com.tongering.backend.pojo.*;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import com.tongering.backend.service.invitation.GetPersonalInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetPersonalInvitationServiceImpl implements GetPersonalInvitationService {
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private InvitationTypeMapper typeMapper;
    @Override
    public List<InvitationAndType> getpersonal() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();
        QueryWrapper<Invitation> invitationQuery = new QueryWrapper<>();
        invitationQuery.eq("create_id", user.getId());
        List<Invitation> invitations = invitationMapper.selectList(invitationQuery);
        List<InvitationType> types = typeMapper.selectList(null);
        List<InvitationAndType> list = new ArrayList<>();
        for(Invitation i : invitations){
            for(InvitationType j : types){
                if(i.getTypeId().equals(j.getId())){
                    InvitationAndType invitationAndType = new InvitationAndType(i.getId(), i.getTitle(),
                                    j.getTypename(), i.getContent(), i.getCreateTime());
                    list.add(invitationAndType);
                    break;
                }
            }
        }
        return list;
    }
}
