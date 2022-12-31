package com.tongering.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.TeacherMapper;
import com.tongering.backend.mapper.TeacherStudentRelationshipMapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.TeacherStudentRelationship;
import com.tongering.backend.pojo.UserAndUserProfile;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.SearchUserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchUserListServiceImpl implements SearchUserListService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper profileMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TeacherStudentRelationshipMapper teacherStudentRelationshipMapper;

    @Override
    public List<UserAndUserProfile> getSearch(String name, String type) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", name);
        List<Users> users = usersMapper.selectList(queryWrapper);
        List<UserAndUserProfile> result = new ArrayList<>();
        if("teacher".equals(type)){
            System.out.println(name + users.size() + " " + result.size());
            for(Users i : users){
                QueryWrapper<TeacherStudentRelationship> qq = new QueryWrapper<>();
                qq.eq("teacher_id", i.getId());
                TeacherStudentRelationship t = teacherStudentRelationshipMapper.selectOne(qq);
                if(t==null){
                    if(teacherMapper.selectById(i.getId())!=null){
                        UserProfile profile = profileMapper.selectById(i.getId());
                        result.add(new UserAndUserProfile(i.getId(), i.getUsername(), profile.getPhoto()));
                    }
                }
            }
        } else if ("peer".equals(type)){
            for(Users i : users){
                QueryWrapper<TeacherStudentRelationship> qq = new QueryWrapper<>();
                qq.eq("teacher_id", i.getId());
                TeacherStudentRelationship t = teacherStudentRelationshipMapper.selectOne(qq);
                if(t==null){
                    UserProfile profile = profileMapper.selectById(i.getId());
                    result.add(new UserAndUserProfile(i.getId(), i.getUsername(), profile.getPhoto()));
                }
            }
        }


        return result;
    }
}
