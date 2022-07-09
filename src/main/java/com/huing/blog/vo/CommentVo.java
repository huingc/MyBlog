package com.huing.blog.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author huing
 * @Create 2022-07-08 15:12
 */
@Data
public class CommentVo {
    //防止前端 精度损失 把id转为string
    // 分布式id 比较长，传到前端 会有精度损失，必须转为string类型 进行传输，就不会有问题了
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 作者信息
     */
    private UserVo author;

    /**
     * 内容
     */
    private String content;

    /**
     * 子评论信息
     */
    private List<CommentVo> childrens;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 评论的是第几层（1级表示最上层的评论，2表示对评论的评论）
     */
    private Integer level;

    /**
     * 给谁评论
     */
    private UserVo toUser;
}
