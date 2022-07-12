package com.huing.blog.service;

import com.huing.blog.vo.CategoryVo;
import com.huing.blog.vo.Result;

/**
 * @author huing
 * @create 2022-07-03 15:20
 */
public interface CategoryService {
    /**
     * 根据id查询分类信息
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 查询所有分类
     * @return
     */
    Result findAll();
}
