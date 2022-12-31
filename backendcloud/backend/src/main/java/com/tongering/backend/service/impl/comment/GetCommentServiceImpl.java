package com.tongering.backend.service.impl.comment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.CommentMapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.Comment;
import com.tongering.backend.pojo.MultiComment;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.comment.GetCommentService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetCommentServiceImpl implements GetCommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Override
    public List<MultiComment> getComment(Integer invitationId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invitation_id", invitationId);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<MultiComment> result = new LinkedList<>();

        for(Comment i : comments){
            Users users = usersMapper.selectById(i.getUserId());
            UserProfile userProfile = userProfileMapper.selectById(i.getUserId());
            MultiComment multiComment = new MultiComment(i.getId(), i.getUserId(), i.getInvitationId(),
                    i.getContent(), i.getCreateTime(), userProfile.getPhoto(), users.getUsername());
            result.add(multiComment);
        }

        return result;
    }
}
