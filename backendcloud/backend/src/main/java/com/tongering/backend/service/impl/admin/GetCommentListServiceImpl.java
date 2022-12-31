package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.CommentMapper;
import com.tongering.backend.mapper.InvitationMapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.Comment;
import com.tongering.backend.pojo.CommentAndUser;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.service.admin.GetCommentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetCommentListServiceImpl implements GetCommentListService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper profileMapper;
    @Override
    public List<CommentAndUser> getlist() {
        List<CommentAndUser> result = new ArrayList<>();
        List<Comment> comments = commentMapper.selectList(null);
        for(Comment i : comments){
            result.add(new CommentAndUser(i.getId(), i.getUserId(),
                    usersMapper.selectById(i.getUserId()).getUsername(),
                    i.getInvitationId(), invitationMapper.selectById(i.getInvitationId()).getTitle(),
                    i.getContent(), i.getCreateTime(), profileMapper.selectById(i.getUserId()).getPhoto()));
        }

        return result;
    }
}
