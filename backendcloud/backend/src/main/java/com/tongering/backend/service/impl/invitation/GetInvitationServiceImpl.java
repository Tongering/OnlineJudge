package com.tongering.backend.service.impl.invitation;

import com.tongering.backend.mapper.InvitationMapper;
import com.tongering.backend.mapper.InvitationTypeMapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.*;
import com.tongering.backend.service.invitation.GetInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetInvitationServiceImpl implements GetInvitationService {
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private InvitationTypeMapper invitationTypeMapper;

    @Override
    public MultiInvitationBrief getinvitation(Integer pageId) {

        Invitation invitation = invitationMapper.selectById(pageId);
        InvitationType invitationType = invitationTypeMapper.selectById(invitation.getTypeId());
        UserProfile userProfile = userProfileMapper.selectById(invitation.getCreateId());
        Users users = usersMapper.selectById(invitation.getCreateId());

        MultiInvitationBrief multiInvitationBrief = new MultiInvitationBrief(
                invitation.getId(), users.getUsername(), userProfile.getPhoto(), invitation.getCreateId(),
                invitationType.getTypename(), invitation.getTitle(), invitation.getContent(), invitation.getCreateTime()
        );

        return multiInvitationBrief;
    }
}
