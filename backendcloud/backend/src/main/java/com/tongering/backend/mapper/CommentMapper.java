package com.tongering.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongering.backend.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
