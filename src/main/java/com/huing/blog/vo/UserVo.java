package com.huing.blog.vo;

import lombok.Data;

/**
 * @author huing
 * @create 2022-06-25 11:11
 */
@Data
public class UserVo {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * id
     */
//    @JsonSerialize(using = ToStringSerializer.class)
    private String  id;
}
