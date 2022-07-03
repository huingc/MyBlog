package com.huing.blog.controller;

import com.huing.blog.service.ArticleService;
import com.huing.blog.vo.ArticleVo;
import com.huing.blog.vo.Result;
import com.huing.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huing
 * @create 2022-07-03 10:20
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 首页，文章处理
     *
     * @param pageParams
     * @return
     */
    @PostMapping()
    public Result<List<ArticleVo>> listArticle(@RequestBody PageParams pageParams) {

        return articleService.listArticle(pageParams);
    }
}
