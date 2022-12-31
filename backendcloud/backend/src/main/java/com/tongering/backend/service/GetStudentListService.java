package com.tongering.backend.service;

import com.tongering.backend.pojo.UserAndUserProfile;
import com.tongering.backend.pojo.Users;

import java.util.List;

public interface GetStudentListService {
    public List<UserAndUserProfile> getList();
}
