package com.tongering.backend.service.invitation;

import com.tongering.backend.pojo.MultiInvitationBrief;

import java.util.List;
import java.util.Map;

public interface SearchInvitationService {
    public List<MultiInvitationBrief> search(Map<String, String> data);
}
