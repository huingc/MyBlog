package com.huing.blog.vo;

import lombok.Data;

/**
 * @author huing
 * @create 2022-06-24 16:00
 */
@Data
public class CategoryVo {
    //id，图标路径，图标名称

//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String avatar;

    private String categoryName;

    private String description;
}
