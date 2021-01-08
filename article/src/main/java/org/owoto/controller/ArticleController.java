package org.owoto.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.owoto.entity.Article;
import org.owoto.entity.PageVO;
import org.owoto.mapper.ArticleMapper;
import org.owoto.service.ArticleService;
import org.owoto.util.RedisUtil;
import org.owoto.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzfn
 * @date 2020-12-08 0:02
 */
@RestController
@Slf4j
@RequestMapping("non")
public class ArticleController {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleService articleService;

    @Autowired
    RedisUtil redisUtil;

    @ApiOperation("文章分页列表")
    @GetMapping("page")
    public Object listArticles(PageVO pageVo) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("IS_RELEASE",0).orderByDesc("ORDER_NUM").orderByDesc("CREATE_TIME");
        IPage<Article> page = new Page<>(pageVo.getPageNumber(), pageVo.getPageSize());
        IPage<Article> pageList = articleMapper.selectPage(page, queryWrapper);
        pageList.getRecords().forEach(article -> {
            if (redisUtil.get(article.getId()) == null) {
                redisUtil.incr(article.getId(), 1);
                article.setViewCount(0L);
            } else {
                article.setViewCount(((Number) redisUtil.get(article.getId())).longValue());
            }
        });
        return ResultUtil.success(pageList);
    }

    @ApiOperation("文章分类")
    @GetMapping("tags")
    public Object listTags() {
        return ResultUtil.success(articleMapper.getTags());
    }

    @ApiOperation("文章列表不分页")
    @GetMapping("list")
    public Object listArchives(String code) {
        return ResultUtil.success(articleMapper.getArchives(code));
    }

    @ApiOperation("根据id查询文章详情")
    @GetMapping("{id}")
    public Object getArticle(@PathVariable("id") String id) {
        Article article = articleMapper.selectById(id);
        if (article != null) {
            article.setViewCount(redisUtil.incr(id, 1));
            return ResultUtil.success(article);
        } else {
            return ResultUtil.error("未找到结果");
        }
    }
}
