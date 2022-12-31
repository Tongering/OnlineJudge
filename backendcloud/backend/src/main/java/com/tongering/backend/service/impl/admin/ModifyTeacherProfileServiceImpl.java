package com.tongering.backend.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.admin.ModifyTeacherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ModifyTeacherProfileServiceImpl implements ModifyTeacherProfileService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper profileMapper;

    @Override
    public Map<String, String> modify(Map<String, String> data) {
        Map<String, String> result = new HashMap<>();

        Integer userId = Integer.parseInt(data.get("teacherId"));
        String userName = data.get("teacherName");
        String photo = data.get("teacherPhoto");
        String email = data.get("teacherEmail");
        String declaration = data.get("teacherDeclaration");
        String joinTime = data.get("teacherJoinTime");
        String background = data.get("teacherBackground");

        UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<>();
        usersUpdateWrapper.eq("id", userId).set("username", userName);
        UpdateWrapper<UserProfile> userProfileUpdateWrapper = new UpdateWrapper<>();
        userProfileUpdateWrapper.eq("id", userId).set("photo", photo).set("email", email).set("declaration", declaration).set("join_time", joinTime).set("background", background);

        usersMapper.update(null, usersUpdateWrapper);
        profileMapper.update(null, userProfileUpdateWrapper);

        result.put("error_message", "success");


        return result;
    }
}
