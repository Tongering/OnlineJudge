package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.TeacherMapper;
import com.tongering.backend.pojo.Teacher;
import com.tongering.backend.service.admin.AddTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class AddTeacherServiceImpl implements AddTeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public Map<String, String> add(Integer userId) {
        teacherMapper.insert(new Teacher(userId));
        Map<String, String> result =new HashMap<>();
        result.put("error_message", "success");
        return result;
    }
}
