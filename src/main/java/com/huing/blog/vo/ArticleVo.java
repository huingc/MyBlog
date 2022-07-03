package com.huing.blog.vo;

import lombok.Data;

import java.util.List;

/**
 * @author huing
 * @create 2022-06-11 20:22
 */

@Data
public class ArticleVo {

    //    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     * 评论数量
     */
    private Integer commentCounts;

    /**
     * 浏览数量
     */
    private Integer viewCounts;

    /**
     * 置顶
     */
    private Integer weight;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 作者
     */
//    private String author;
    private UserVo author;


    private ArticleBodyVo body;

    /**
     * 标签
     */
    private List<TagVo> tags;

    private CategoryVo category;

}