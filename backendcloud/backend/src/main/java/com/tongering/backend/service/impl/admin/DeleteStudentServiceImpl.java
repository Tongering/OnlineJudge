package com.tongering.backend.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.*;
import com.tongering.backend.pojo.*;
import com.tongering.backend.service.admin.DeleteStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteStudentServiceImpl implements DeleteStudentService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper profileMapper;
    @Autowired
    private TeacherStudentRelationshipMapper relationshipMapper;
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CodeRecordMapper recordMapper;
    // exercise exercise_type exercise_test 不删除
    @Override
    public Map<String, String> delete(Integer studentId) {
        Map<String, String> result = new HashMap<>();

        usersMapper.deleteById(studentId);

        QueryWrapper<UserProfile> userProfileQueryWrapper = new QueryWrapper<>();
        userProfileQueryWrapper.eq("id", studentId);
        profileMapper.delete(userProfileQueryWrapper);

        QueryWrapper<TeacherStudentRelationship> teacherStudentRelationshipQueryWrapper = new QueryWrapper<>();
        teacherStudentRelationshipQueryWrapper.eq("teacher_id", studentId);
        relationshipMapper.delete(teacherStudentRelationshipQueryWrapper);

        QueryWrapper<Invitation> invitationQueryWrapper = new QueryWrapper<>();
        invitationQueryWrapper.eq("create_id", studentId);
        invitationMapper.delete(invitationQueryWrapper);

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("user_id", studentId);
        commentMapper.delete(commentQueryWrapper);

        QueryWrapper<CodeRecord> codeRecordQueryWrapper = new QueryWrapper<>();
        codeRecordQueryWrapper.eq("user_id", studentId);
        recordMapper.delete(codeRecordQueryWrapper);

        result.put("error_message", "success");

        return result;
    }
}
