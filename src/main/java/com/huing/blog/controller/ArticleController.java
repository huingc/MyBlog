package com.huing.blog.controller;

import com.huing.blog.service.ArticleService;
import com.huing.blog.vo.ArticleVo;
import com.huing.blog.vo.Result;
import com.huing.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${article.hotAndNew.limit}")
    private int limit;


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

    /**
     * 最热文章
     * @return
     */
    @PostMapping("hot")
    public Result hotArticle(){
        return articleService.hotArticle(limit);
    }

    /**
     * 最新文章
     * @return
     */
    @PostMapping("new")
    public Result newArticle(){
        return  articleService.newArticle(limit);
    }

    /**
     * 首页——文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listarchives();
    }
}
