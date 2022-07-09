package com.huing.blog.service;

import com.huing.blog.vo.Result;
import com.huing.blog.vo.params.CommentParam;

/**
 * @Author huing
 * @Create 2022-07-08 14:56
 */
public interface CommentsService {
    /**
     * 获取评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    /**
     * 评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
