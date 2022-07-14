package com.huing.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huing.blog.dao.mapper.TagMapper;
import com.huing.blog.dao.pojo.Tag;
import com.huing.blog.service.ArticleService;
import com.huing.blog.service.TagService;
import com.huing.blog.vo.Result;
import com.huing.blog.vo.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author huing
 * @create 2022-07-03 11:30
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleService articleService;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hots(int limit) {
        /**
         *  1.标签所拥的文章数量最多
         *  2.根据tag_id分组计数，从大到小排列，取前limit
         */
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);

        if (CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }

        //我们需要tagId和tagName
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tagList = tagMapper.selectList(null);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }

    private List<TagVo> copyList(List<Tag> tags) {
        List<TagVo> tagVoList = new ArrayList<>();

        for (Tag tag : tags) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }


}
