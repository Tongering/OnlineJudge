package com.tongering.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongering.backend.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper extends BaseMapper<Users> {
}
