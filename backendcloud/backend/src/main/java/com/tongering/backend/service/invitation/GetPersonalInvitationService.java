package com.tongering.backend.service.invitation;

import com.tongering.backend.pojo.Invitation;
import com.tongering.backend.pojo.InvitationAndType;
import com.tongering.backend.pojo.MultiInvitationBrief;

import java.util.List;

public interface GetPersonalInvitationService {
    public List<InvitationAndType> getpersonal ();
}
