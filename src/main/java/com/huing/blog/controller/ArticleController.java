package com.huing.blog.controller;

import com.huing.blog.common.aop.LogAnnotation;
import com.huing.blog.common.cache.Cache;
import com.huing.blog.service.ArticleService;
import com.huing.blog.vo.ArticleVo;
import com.huing.blog.vo.Result;
import com.huing.blog.vo.params.ArticleParm;
import com.huing.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

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
    //加上此注解，代表对此接口记录日志
    @LogAnnotation(module = "文章", operation = "获取文章列表")
    public Result<List<ArticleVo>> listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 最热文章
     *
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result hotArticle() {
        return articleService.hotArticle(limit);
    }

    /**
     * 最新文章
     *
     * @return
     */
    @PostMapping("new")
    @Cacheable(value = {"newArticle"}, key = "#root.methodName",cacheManager = "cacheManager1Minute")
    public Result newArticle() {
        return articleService.newArticle(limit);
    }

    /**
     * 首页——文章归档
     *
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        return articleService.listarchives();
    }

    /**
     * 根据文章id查询文章详情
     *
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
//    @Cacheable(value = {"ArticleById"}, key = "#articleId")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * 写文章
     *
     * @param articleParm
     * @return
     */
    //  @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
    //  而最常用的使用请求体传参的无疑是POST请求了，所以使用@RequestBody接收数据时，一般都用POST方式进行提交。
    @PostMapping("publish")
//    @CachePut(value = {"ArticleById"},key = "#articleParm.id")
    public Result publish(@RequestBody ArticleParm articleParm) {
        return articleService.publish(articleParm);
    }

    /**
     * 修改文章时，获取文章详情
     */
    @PostMapping("{id}")
    public Result articleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

//    @PostMapping("search")
//    public Result search(@RequestBody ArticleParam articleParam){
//        //搜索接口
//        String search = articleParam.getSearch();
//        return articleService.searchArticle(search);
//    }
}
