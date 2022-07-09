package com.huing.blog.controller;

import com.huing.blog.service.CommentsService;
import com.huing.blog.vo.Result;
import com.huing.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author huing
 * @Create 2022-07-08 14:53
 */
@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    /**
     * 获取评论列表
     * @param id
     * @return
     */
    @GetMapping("article/{id}")
    public Result article(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }

    /**
     * 评论
     * @param commentParam
     * @return
     */
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
