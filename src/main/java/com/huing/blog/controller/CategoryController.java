package com.huing.blog.controller;

import com.huing.blog.service.CategoryService;
import com.huing.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 查询所有文章分类
     * @return
     */
    @GetMapping("detail")
    public Result detail(){
        return categoryService.findAllDetail();
    }

    /**
     * 分类文章列表
     * @param id
     * @return
     */
    @GetMapping("detail/{id}")
    public Result categoryDetailById(@PathVariable("id") Long id){
        return categoryService.categoryDetailById(id);
    }
}
