package com.huing.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.huing.blog.dao.mapper.SysUserMapper;
import com.huing.blog.dao.pojo.SysUser;
import com.huing.blog.service.LoginService;
import com.huing.blog.service.SysUserService;
import com.huing.blog.utils.JWTUtils;
import com.huing.blog.vo.ErrorCode;
import com.huing.blog.vo.Result;
import com.huing.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author huing
 * @create 2022-07-04 15:29
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${user.pwd.salt}")
    private String salt;

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码去user表查询 是否存在
         * 3.不存在，登陆失败
         * 4.存在，使用JWT   生成token     返回给前端
         * 5.token放入redis中，redis    token： user信息   设置过期时间（登录认证的时候，先认证token字符串是否合法，去redis认证是否合法）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        String newPwd = DigestUtils.md5Hex(password + salt);
        SysUser user = sysUserService.findUser(account, newPwd);
        if (user == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        // 使用JWT   生成token
        String token = JWTUtils.createToken(user.getId());

        //有可能在你退出登录前token被盗取，加入redis你退出登录的时候也清除redis
        //token放入redis中,过期时间是一天
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (userJson == null) {
            return null;
        }

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);

        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1.判断参数是否合法
         * 2.判断账户是否存在   存在，返回账户已经被注册
         * 3.如果不存在，注册用户
         * 4.生成token
         * 5.存入redis并返回
         * 6.注意，加上事务，中间任何过程出现问题，注册的用户需要回滚
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser user = sysUserService.findUserByAccount(account);
        if (user != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }

        //注册用户
        SysUser sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.9d2457d.png");
        sysUser.setAdmin1(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);

        //生成token
        String token = JWTUtils.createToken(sysUser.getId());

        //保存到redis中
        redisTemplate.opsForValue().set("TOKEN_" + token,JSON.toJSONString(sysUser),1,TimeUnit.DAYS);

        return Result.success(token);
    }
}
