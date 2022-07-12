package com.huing.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huing.blog.dao.dos.Archives;
import com.huing.blog.dao.mapper.ArticleBodyMapper;
import com.huing.blog.dao.mapper.ArticleMapper;
import com.huing.blog.dao.mapper.ArticleTagMapper;
import com.huing.blog.dao.pojo.Article;
import com.huing.blog.dao.pojo.ArticleBody;
import com.huing.blog.dao.pojo.ArticleTag;
import com.huing.blog.dao.pojo.SysUser;
import com.huing.blog.service.*;
import com.huing.blog.utils.UserThreadLocal;
import com.huing.blog.vo.*;
import com.huing.blog.vo.params.ArticleParm;
import com.huing.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huing
 * @create 2022-07-03 11:01
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result<List<ArticleVo>> listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records, true, true));
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listarchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Autowired
    private ThreadService threadService;

    @Override
    public Result findArticleById(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article,true,true,true,true);

        //查看完文章了，新增阅读数，有没有问题呢？
        //查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低（没办法解决，增加阅读数必然要加锁）
        //更新增加了此次接口的耗时（考虑减少耗时）如果一旦更新出问题，不能影响查看操作
        //线程池   可以把更新操作扔到 线程池中去执行和主线程就不相关了
        //threadService.updateArticleViewCount(articleMapper, article);

        //在这里我们采用redis  incr自增实现
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParm articleParm) {
        //加入到登录拦截中
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1.发布文章 目的是构建Article对象
         * 2.作者id 当前的登录用户
         * 3.标签 要将标签加入到 关联列表中
         * 4.body 内容存储 article bodyId
         */
        Article article = new Article();

        if (articleParm.getId() != null){
            article.setId(articleParm.getId());
            article.setTitle(articleParm.getTitle());
            article.setSummary(articleParm.getSummary());
            article.setCategoryId(Long.parseLong(articleParm.getCategory().getId()));
            articleMapper.insert(article);
        }else {
            article = new Article();
            article.setAuthorId(sysUser.getId());
            article.setWeight(Article.Article_Common);
            article.setViewCounts(0);
            article.setTitle(articleParm.getTitle());
            article.setSummary(articleParm.getSummary());
            article.setCommentCounts(0);
            article.setCreateDate(System.currentTimeMillis());
            article.setCategoryId(Long.parseLong(articleParm.getCategory().getId()));
            articleMapper.insert(article);
        }

        //tag
        List<TagVo> tags = articleParm.getTags();
        if (tags != null){
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }

        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParm.getBody().getContent());
        articleBody.setContentHtml(articleParm.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        //并不是所有的接口，都需要标签，作者信息
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.findUserById(authorId);
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(sysUser, userVo);
            userVo.setId(String.valueOf(sysUser.getId()));
            articleVo.setAuthor(userVo);
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findarticleBodyByid(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }


    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    /**
     * 根据BodyId查询 文章详情内容
     *
     * @param bodyId
     * @return
     */
    private ArticleBodyVo findarticleBodyByid(Long bodyId) {

        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    @Override
    public Article getArticleById(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        return article;
    }

    @Override
    public List<Article> findArticleAll() {
        List<Article> articleList = articleMapper.selectList(null);
        return articleList;
    }

    @Override
    public Boolean updateNumById(Article article) {
        return articleMapper.updateNumById(article) > 0;
    }
}
