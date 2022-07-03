package com.huing.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huing.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huing
 * @create 2022-06-11 19:05
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询前limi个最热标签id
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 根据标签id查询标签详情
     * @param tagIds
     * @return
     */
    List<Tag> findTagsByTagIds(@Param("tagIds") List<Long> tagIds);

}
