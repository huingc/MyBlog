package com.huing.blog.service;

import com.huing.blog.dao.pojo.SysUser;

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
}
