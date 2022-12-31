package com.tongering.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.TeacherStudentRelationshipMapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.*;
import com.tongering.backend.service.CheckIsTeacherService;
import com.tongering.backend.service.GetStudentListService;
import com.tongering.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GetStudentListServiceImpl implements GetStudentListService {
    @Autowired
    private TeacherStudentRelationshipMapper mapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private CheckIsTeacherService checkIsTeacherService;
    @Override
    public List<UserAndUserProfile> getList() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginuser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Users user = loginuser.getUsers();

        Map<String, String> isTeacher = checkIsTeacherService.check();
        if(isTeacher.get("error_message").equals("admin_false")){
            return null;
        }

        QueryWrapper<TeacherStudentRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", user.getId());
        List<TeacherStudentRelationship> list = mapper.selectList(queryWrapper);
        List<UserAndUserProfile> result= new ArrayList<>();
        for(TeacherStudentRelationship i : list){
            String username = usersMapper.selectById(i.getStudentId()).getUsername();
            String photo = userProfileMapper.selectById(i.getStudentId()).getPhoto();
            UserAndUserProfile userAndUserProfile = new UserAndUserProfile(i.getStudentId(), username, photo);
            result.add(userAndUserProfile);
        }

        return result;
    }
}
