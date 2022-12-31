package com.tongering.backend.service.invitation;

import com.tongering.backend.pojo.Invitation;
import com.tongering.backend.pojo.MultiInvitationBrief;

import java.util.List;
import java.util.Map;

public interface GetInvitationBriefService {
    public List<MultiInvitationBrief> getBrief(Map<String, String>data);
}
