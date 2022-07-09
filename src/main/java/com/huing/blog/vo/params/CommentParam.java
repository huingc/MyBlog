package com.huing.blog.vo.params;

import lombok.Data;

/**
 * @Author huing
 * @Create 2022-07-08 16:24
 */
@Data
public class CommentParam {

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论id
     */
    private Long parent;

    /**
     * 被评论的用户id
     */
    private Long toUserId;
}

