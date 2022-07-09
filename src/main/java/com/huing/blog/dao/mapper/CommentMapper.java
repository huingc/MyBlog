package com.huing.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huing.blog.dao.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author huing
 * @Create 2022-07-08 15:01
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
