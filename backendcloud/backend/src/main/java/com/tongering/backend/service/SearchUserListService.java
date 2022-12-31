package com.tongering.backend.service;

import com.tongering.backend.pojo.UserAndUserProfile;

import java.util.List;

public interface SearchUserListService {
    public List<UserAndUserProfile> getSearch(String name, String type);
}
