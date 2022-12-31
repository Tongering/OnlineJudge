package com.tongering.backend.service.comment;

import com.tongering.backend.pojo.Comment;
import com.tongering.backend.pojo.MultiComment;

import java.util.List;

public interface GetCommentService {
    public List<MultiComment> getComment(Integer invitationId);
}
