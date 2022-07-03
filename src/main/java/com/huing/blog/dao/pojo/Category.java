package com.huing.blog.dao.pojo;

import lombok.Data;

/**
 * 类别表
 * @author huing
 * @create 2022-06-24 15:52
 */
@Data
public class Category {

    private Long id;

    /**
     * 分类图标路径
     */
    private String avatar;

    /**
     * 图标分类的名称
     */
    private String categoryName;

    /**
     * 分类的描述
     */
    private String description;
}
