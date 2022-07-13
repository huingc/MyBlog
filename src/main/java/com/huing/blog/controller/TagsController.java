package com.huing.blog.controller;

import com.huing.blog.service.TagService;
import com.huing.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huing
 * @create 2022-07-03 16:11
 */
@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @Value("${tag.hot.limit}")
    private int limit;

    /**
     * 最热标签
     * @return
     */
    @GetMapping("hot")
    public Result hot(){
        return tagService.hots(limit);
    }

    /**
     * 所有标签
     * @return
     */
    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    /**
     * 查询所有标签
     * @return
     */
    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }
}
