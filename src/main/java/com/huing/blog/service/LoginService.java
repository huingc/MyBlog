package com.huing.blog.service;

import com.huing.blog.dao.pojo.SysUser;
import com.huing.blog.vo.Result;
import com.huing.blog.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huing
 * @create 2022-07-04 15:29
 */
@Transactional
public interface LoginService {
    /**
     * JWT登录
     *
     * @param loginParam
     */
    Result login(LoginParam loginParam);

    /**
     * 检验token合法性，拿到redis中的user信息
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
