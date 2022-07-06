package com.huing.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huing.blog.dao.mapper.SysUserMapper;
import com.huing.blog.dao.pojo.SysUser;
import com.huing.blog.service.LoginService;
import com.huing.blog.service.SysUserService;
import com.huing.blog.vo.ErrorCode;
import com.huing.blog.vo.LoginUserVo;
import com.huing.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huing
 * @create 2022-07-03 11:47
 */
@Service
public class SysUserServiceImpl implements SysUserService{

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("huing");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String newPwd) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,newPwd);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token合法性校验
         *      是否为空，解析是否成功，redis是否存在
         * 2.如果校验失败，返回错误
         * 3.如果成功，返回对应结果LoginUserVo
         */
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(sysUser,loginUserVo);
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public void save(SysUser sysUser) {
        //保存用户 id自动生成
        //这个地方 默认生成的id是 分布式id 雪花算法
        //mybatis-plus
        sysUserMapper.insert(sysUser);
    }
}
