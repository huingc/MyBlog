package com.huing.blog.common;

import com.huing.blog.service.ArticleService;
import com.huing.blog.utils.RedisUtil;
import com.huing.blog.vo.params.CommentParam;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author huing
 * @Create 2022-07-06 17:46
 */
//指定为切面类
@Aspect
@Component
public class MyAspect {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisUtil redisUtil;

    //定义一个名为"myPointCut_ViewNum()"的切面，getById()这个方法中
    @Pointcut("execution(public * com.huing.blog.controller.ArticleController.findArticleById(..))")
    public void myPointCut_ViewNum() { }

    @Pointcut("execution(public * com.huing.blog.controller.CommentsController.comment(..))")
    public void myPointCut_CommentNum(){}

    //在这个方法执行后
    @After("myPointCut_ViewNum()")
    public void doAfter(JoinPoint joinPoint) throws Throwable {
        Object[] objs = joinPoint.getArgs();
        Long id = (Long) objs[0];
        //根据id更新浏览量
        redisUtil.zIncrementScore("viewNum", id.toString(), 1);
    }

    @After("myPointCut_CommentNum()")
    public void doAfter_commentNum(JoinPoint joinPoint){
        Object[] objs = joinPoint.getArgs();
        CommentParam commentParam = (CommentParam) objs[0];

        //根据id更新评论数
        redisUtil.zIncrementScore("commentNum",commentParam.getArticleId().toString(),1);
    }

}