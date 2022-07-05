package com.huing.blog.controller;

import com.huing.blog.service.LoginService;
import com.huing.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huing
 * @Create 2022-07-05 15:38
 */
@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    /**
     * 退出登录
     * @return
     */
    @GetMapping
    private Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
