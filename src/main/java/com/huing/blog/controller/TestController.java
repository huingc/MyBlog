package com.huing.blog.controller;

import com.huing.blog.dao.pojo.SysUser;
import com.huing.blog.utils.UserThreadLocal;
import com.huing.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huing
 * @create 2022-06-24 13:34
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.err.println(sysUser);
        return Result.success(null);
    }
}
