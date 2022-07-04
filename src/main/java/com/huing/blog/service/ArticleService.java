package com.huing.blog.service;

import com.huing.blog.vo.ArticleVo;
import com.huing.blog.vo.Result;
import com.huing.blog.vo.params.PageParams;

import java.util.List;

/**
 * @author huing
 * @create 2022-07-03 10:42
 */
public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result<List<ArticleVo>> listArticle(PageParams pageParams);

    /**
     * 查询前limit个最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 首页-文章归档
     * SELECT YEAR(FROM_UNIXTIME(create_date/1000)) YEAR,MONTH(FROM_UNIXTIME(create_date/1000)) MONTH, COUNT(*) COUNT FROM ms_article GROUP BY YEAR,MONTH;
     * @return
     */
    Result listarchives();
}
