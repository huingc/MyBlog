package com.huing.blog.service;

import com.huing.blog.dao.pojo.SysUser;
import com.huing.blog.vo.Result;

/**
 * @author huing
 * @create 2022-07-03 11:47
 */
public interface SysUserService {

    /**
     * 根据id查询user
     * @param id
     * @return
     */
    SysUser findUserById(Long id);

    /**
     * 根据用户名密码查询用户数据
     * @param account
     * @param newPwd
     * @return
     */
    SysUser findUser(String account, String newPwd);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据用户名查找用户信息
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户信息
     * @param sysUser
     */
    void save(SysUser sysUser);
}
