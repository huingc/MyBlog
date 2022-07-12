package com.huing.blog.service;

import com.huing.blog.vo.Result;
import com.huing.blog.vo.TagVo;

import java.util.List;

/**
 * @author huing
 * @create 2022-07-03 11:30
 */
public interface TagService {
    /**
     * 根据文章id查询该文章的tag标签
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 获取limit个最热标签
     * @param limit
     */
    Result hots(int limit);

    /**
     * 获取所有标签
     * @return
     */
    Result findAll();
}
