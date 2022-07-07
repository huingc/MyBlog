package com.huing.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huing.blog.dao.dos.Archives;
import com.huing.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author huing
 * @create 2022-06-11 19:00
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 根据条件查询文章详情
     *
     * @param page       代表我们自定义的分页查询
     * @param categoryId 类别id
     * @param tagId      标签id
     * @param year       年
     * @param month      月
     * @return
     */
    //Page对象 是mybatisplus的page对象 代表要用到mybatisplus的一个分页
    //IPage也是mybatisplus的，代表我们自定义的分页查询
    IPage<Article> listArticle(@Param("page") Page<Article> page,
                               @Param("categoryId") Long categoryId,
                               @Param("tagId") Long tagId,
                               @Param("year") String year,
                               @Param("month") String month);

    /**
     * 文章归档
     *
     *  select year(from_unixtime(create_date / 1000))  year,
     *  month(from_unixtime(create_date / 1000)) month,
     *  count(*)                                 count
     *  from ms_article
     *  group by year, month
     *
     * @return
     */
    List<Archives> listArchives();




    /**
     * 更新（浏览量，评论数）
     * @param article
     * @return
     */
    int updateNumById(Article article);
}
