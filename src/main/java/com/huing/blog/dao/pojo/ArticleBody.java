package com.huing.blog.dao.pojo;

import lombok.Data;

/**
 * 内容表
 * @author huing
 * @create 2022-06-24 15:48
 */
@Data
public class ArticleBody {

    private Long id;
    /**
     * makedown格式的信息
     */
    private String content;

    /**
     * html格式的信息
     */
    private String contentHtml;
    private Long articleId;
}