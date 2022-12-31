package com.tongering.backend.service.admin;

import com.tongering.backend.pojo.ProfileAndUser;
import com.tongering.backend.pojo.UserAndUserProfile;

import java.util.List;

public interface GetAdminListService {
    public List<ProfileAndUser> getList();

}
