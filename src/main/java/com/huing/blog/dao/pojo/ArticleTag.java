package com.huing.blog.dao.pojo;

import lombok.Data;

/**
 * @Author huing
 * @Create 2022-07-12 14:01
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
