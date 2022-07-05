package com.huing.blog.vo.params;

import lombok.Data;

/**
 * @author huing
 * @create 2022-07-04 15:33
 */
@Data
public class LoginParam {

    private String account;

    private String password;

    private String nickname;
}
