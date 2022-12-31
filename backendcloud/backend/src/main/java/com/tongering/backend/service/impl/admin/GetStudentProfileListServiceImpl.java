package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.AdminMapper;
import com.tongering.backend.mapper.TeacherMapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.*;
import com.tongering.backend.service.admin.GetStudentProfileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetStudentProfileListServiceImpl implements GetStudentProfileListService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper profileMapper;

    @Override
    public List<ProfileAndUser> getList() {
        List<ProfileAndUser> result = new ArrayList<>();

        List<Teacher> teacherList = teacherMapper.selectList(null);
        List<Admin> admins = adminMapper.selectList(null);
        List<Users> usersList = usersMapper.selectList(null);
        List<Integer> student = new ArrayList<>();
        for(Users i : usersList){
            student.add(i.getId());
        }

        for(Teacher i : teacherList){
            for(Integer j : student){
                if(i.getId().equals(j)){
                    student.remove(i.getId());
                    break;
                }
            }
        }
        for(Admin i : admins){
            for(Integer j : student){
                if(i.getId().equals(j)){
                    student.remove(i.getId());
                    break;
                }
            }
        }

        for(Integer i : student){
            String username = usersMapper.selectById(i).getUsername();
            UserProfile profile = profileMapper.selectById(i);
            result.add(new ProfileAndUser(i, username, profile.getPhoto(), profile.getEmail(),
                    profile.getDeclaration(),profile.getJoinTime(), profile.getBackground()));
        }

        return result;
    }
}
