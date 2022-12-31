package com.tongering.backend.service.impl.admin;

import com.tongering.backend.mapper.AdminMapper;
import com.tongering.backend.mapper.UserProfileMapper;
import com.tongering.backend.mapper.UsersMapper;
import com.tongering.backend.pojo.Admin;
import com.tongering.backend.pojo.ProfileAndUser;
import com.tongering.backend.pojo.UserAndUserProfile;
import com.tongering.backend.pojo.UserProfile;
import com.tongering.backend.service.admin.GetAdminListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAdminListServiceImpl implements GetAdminListService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserProfileMapper profileMapper;

    @Override
    public List<ProfileAndUser> getList() {
        List<ProfileAndUser> result = new ArrayList<>();
        List<Admin> admins = adminMapper.selectList(null);
        for(Admin i : admins){
            UserProfile profile = profileMapper.selectById(i.getId());
            result.add(new ProfileAndUser(i.getId(), usersMapper.selectById(i.getId()).getUsername(),
                    profile.getPhoto(), profile.getEmail(), profile.getDeclaration(), profile.getJoinTime(), profile.getBackground()));

        }


        return result;
    }
}
