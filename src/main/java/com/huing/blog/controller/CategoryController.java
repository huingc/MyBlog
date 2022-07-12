package com.huing.blog.controller;

import com.huing.blog.service.CategoryService;
import com.huing.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huing
 * @Create 2022-07-09 16:45
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 所有分类
     * @return
     */
    @GetMapping
    public Result categories() {
        return categoryService.findAll();
    }
}
