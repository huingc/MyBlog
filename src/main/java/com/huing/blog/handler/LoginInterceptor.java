package com.huing.blog.handler;

import com.alibaba.fastjson.JSON;
import com.huing.blog.dao.pojo.SysUser;
import com.huing.blog.service.LoginService;
import com.huing.blog.vo.ErrorCode;
import com.huing.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author huing
 * @Create 2022-07-05 18:10
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法(在springMVC中叫做handler）之前进行执行
        /**
         * 1.需要判断请求的接口路径是否为HandlerMethod（Controller方法）
         * 2.判断token是否为空 如果为空 未登录
         * 3.如果token不为空，登录验证 loginService checkToken
         * 4.如果认证成功，放行即可
         */
        //如果不是我们的方法进行放行
        if (!(handler instanceof HandlerMethod)) {
            //handler 可能是访问资源的RequestResourceHandler springboot程序访问静态资源默认去classpath下的static目录去查询
            return false;
        }

        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (token == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            //设置浏览器识别返回的是json
            response.setContentType("application/json;charset=utf-8");
            //https://www.cnblogs.com/qlqwjy/p/7455706.html response.getWriter().print()
            //SON.toJSONString则是将对象转化为Json字符串
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            //设置浏览器识别返回的是json
            response.setContentType("application/json;charset=utf-8");
            //https://www.cnblogs.com/qlqwjy/p/7455706.html response.getWriter().print()
            //SON.toJSONString则是将对象转化为Json字符串
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        //登陆成功放行
        //我希望在controller中直接获取用户的信息怎么获取
        return true;
    }
}
