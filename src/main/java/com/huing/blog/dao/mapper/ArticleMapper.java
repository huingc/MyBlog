package com.huing.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huing.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author huing
 * @create 2022-06-11 19:00
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 根据条件查询文章详情
     * @param page          代表我们自定义的分页查询
     * @param categoryId    类别id
     * @param tagId         标签id
     * @param year          年
     * @param month         月
     * @return
     */
    //Page对象 是mybatisplus的page对象 代表要用到mybatisplus的一个分页
    //IPage也是mybatisplus的，代表我们自定义的分页查询
    IPage<Article> listArticle(@Param("page") Page<Article> page,
                               @Param("categoryId") Long categoryId,
                               @Param("tagId") Long tagId,
                               @Param("year") String year,
                               @Param("month") String month);
}
