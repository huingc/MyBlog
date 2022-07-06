package com.huing.blog.utils;

import com.huing.blog.dao.pojo.SysUser;

/**
 * @Author huing
 * @Create 2022-07-06 10:26
 */
public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser user){
        LOCAL.set(user);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
