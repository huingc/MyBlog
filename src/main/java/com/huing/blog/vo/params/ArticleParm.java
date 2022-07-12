package com.huing.blog.vo.params;

import com.huing.blog.vo.CategoryVo;
import com.huing.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @Author huing
 * @Create 2022-07-12 13:46
 */
@Data
public class ArticleParm {
    /**
     * 文章id（编辑有值）
     */
    private Long id;

    /**
     *文章内容
     */
    private ArticleBodyParam body;

    /**
     *文章类别
     */
    private CategoryVo category;

    /**
     * 文章概述
     */
    private String summary;

    /**
     * 文章标签
     */
    private List<TagVo> tags;

    /**
     * 文章标题
     */
    private String title;

    private String search;
}
