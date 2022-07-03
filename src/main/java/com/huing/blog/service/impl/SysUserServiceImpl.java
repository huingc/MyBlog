package com.huing.blog.service.impl;

import com.huing.blog.dao.mapper.SysUserMapper;
import com.huing.blog.dao.pojo.SysUser;
import com.huing.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huing
 * @create 2022-07-03 11:47
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("huing");
        }
        return sysUser;
    }
}
