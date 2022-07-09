package com.huing.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huing.blog.dao.mapper.CommentMapper;
import com.huing.blog.dao.pojo.Article;
import com.huing.blog.dao.pojo.Comment;
import com.huing.blog.dao.pojo.SysUser;
import com.huing.blog.service.CommentsService;
import com.huing.blog.service.SysUserService;
import com.huing.blog.utils.UserThreadLocal;
import com.huing.blog.vo.CommentVo;
import com.huing.blog.vo.Result;
import com.huing.blog.vo.UserVo;
import com.huing.blog.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huing
 * @Create 2022-07-08 14:56
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long id) {
        /**
         * 1.根据文章id查询评论列表   从comment表查询
         * 2.根据作者id查询作者信息
         * 3.判断如果level = 1 要去查询有没有子评论
         * 4.如果有    根据评论id  进行查询（parent_id）
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        queryWrapper.eq(Comment::getLevel, 1);

        List<Comment> comments = commentMapper.selectList(queryWrapper);

        return Result.success(copyList(comments));
    }

    @Override
    public Result comment(CommentParam commentParam) {
        //拿到当前用户
        SysUser user = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(user.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }

        //如果是空，parent就是0
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);

//        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("id",comment.getArticleId());
//        updateWrapper.setSql(true,"comment_counts=comment_counts+1");
//        this.articleMapper.update(null,updateWrapper);
        //这里我们用redis实现

        CommentVo commentVo = copy(comment);

        return Result.success(commentVo);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {

        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(String.valueOf(comment.getId()));

        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);

        //子评论
        Integer level = comment.getLevel();
        if (level == 1) {
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }

        //to user 给谁评论
        if (level > 1) {
            Long toUid = comment.getToUid();
            UserVo userVoById = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(userVoById);
        }

        //最后是创建时间
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        return commentVo;
    }

    /**
     * 根据ParentId父评论Id获取comment
     *
     * @param id
     * @return
     */
    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);

        return copyList(commentMapper.selectList(queryWrapper));
    }
}
