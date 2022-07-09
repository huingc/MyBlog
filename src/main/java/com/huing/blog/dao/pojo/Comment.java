package com.huing.blog.dao.pojo;

import lombok.Data;

/**
 * @Author huing
 * @Create 2022-07-08 15:01
 */
@Data
public class Comment {

    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Long createDate;

    /**
     * 评论文章
     */
    private Long articleId;

    /**
     * 谁评论的
     */
    private Long authorId;

    /**
     * 盖楼功能对评论的评论进行回复
     */
    private Long parentId;

    /**
     * 给谁评论
     */
    private Long toUid;

    /**
     * 评论的是第几层（1级表示最上层的评论，2表示对评论的评论）
     */
    private Integer level;
}

