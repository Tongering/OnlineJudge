package com.tongering.backend.service.comment;

import java.util.Map;

public interface DeleteCommentService {
    public Map<String, String> delete(Integer id, Integer invitationUserId);
}
