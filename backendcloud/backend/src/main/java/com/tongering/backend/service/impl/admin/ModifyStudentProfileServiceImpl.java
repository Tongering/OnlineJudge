package com.tongering.backend.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.pojo.Users;
import com.tongering.backend.service.admin.ModifyStudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModifyStudentProfileServiceImpl implements ModifyStudentProfileService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper profileMapper;
    @Override
    public Map<String, String> modify(Map<String, String> data) {
        Map<String, String> result = new HashMap<>();

        Integer studentId = Integer.parseInt(data.get("studentId"));
        String studentName = data.get("studentName");
        String studentPhoto = data.get("studentPhoto");
        String studentEmail = data.get("studentEmail");
        String studentDeclaration = data.get("studentDeclaration");
        String studentJoinTime = data.get("studentJoinTime");
        String studentBackground = data.get("studentBackground");

        UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<>();
        usersUpdateWrapper.eq("id", studentId).set("username", studentName);
        UpdateWrapper<UserProfile> profileUpdateWrapper = new UpdateWrapper<>();
        profileUpdateWrapper.eq("id", studentId).set("photo", studentPhoto).set("email", studentEmail)
                .set("declaration", studentDeclaration).set("join_time", studentJoinTime).set("background", studentBackground);
        result.put("error_message", "success");

        usersMapper.update(null, usersUpdateWrapper);
        profileMapper.update(null, profileUpdateWrapper);

        return result;
    }
}
