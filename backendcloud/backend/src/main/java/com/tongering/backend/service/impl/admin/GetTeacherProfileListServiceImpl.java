package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.TeacherMapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.Teacher;
import com.tongering.backend.pojo.ProfileAndUser;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.admin.GetTeacherProfileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetTeacherProfileListServiceImpl implements GetTeacherProfileListService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private UserProfileMapper profileMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public List<ProfileAndUser> getList() {
        List<ProfileAndUser> result = new ArrayList<>();
        List<Teacher> teacherList = teacherMapper.selectList(null);
        for(Teacher i : teacherList){
            UserProfile profile = profileMapper.selectById(i.getId());
            Users users = usersMapper.selectById(i.getId());
            result.add(new ProfileAndUser(i.getId(), users.getUsername(),
                    profile.getPhoto(), profile.getEmail(), profile.getDeclaration(),
                    profile.getJoinTime(), profile.getBackground()));
        }

        return result;
    }
}
