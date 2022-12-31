package com.tongering.backend.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.backend.mapper.TeacherMapper;
import com.tongering.backend.mapper.TeacherStudentRelationshipMapper;
import com.tongering.backend.pojo.Teacher;
import com.tongering.backend.pojo.TeacherStudentRelationship;
import com.tongering.backend.service.admin.DeleteTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteTeacherServiceImpl implements DeleteTeacherService {
    @Autowired
    private TeacherMapper mapper;
    @Autowired
    private TeacherStudentRelationshipMapper relationshipMapper;
    @Override
    public Map<String, String> delete(Integer teacherId) {
        Map<String, String> result = new HashMap<>();
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", teacherId);
        QueryWrapper<TeacherStudentRelationship> relationshipQueryWrapper = new QueryWrapper<>();
        relationshipQueryWrapper.eq("teacher_id", teacherId);
        relationshipMapper.delete(relationshipQueryWrapper);
        mapper.delete(queryWrapper);
        result.put("error_message", "success");
        return result;
    }
}
