package com.tongering.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongering.backend.pojo.UserProfile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserProfileMapper extends BaseMapper<UserProfile> {
}
