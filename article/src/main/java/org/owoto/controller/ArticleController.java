package org.owoto.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.owoto.mapper.ArticleESDao;
import org.owoto.mapper.ArticleMapper;
import org.owoto.entity.Article;
import org.owoto.service.ArticleService;
import org.owoto.util.RedisUtil;
import org.owoto.util.ResultUtil;
import org.owoto.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzfn
 * @date 2020-12-08 0:02
 */
@RestController
@RequestMapping("v1/white")
@Slf4j
public class ArticleController {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    private ArticleESDao articleESDao;
    @Autowired
    ArticleService articleService;
    @Autowired
    RedisUtil redisUtil;

    @PostMapping("article")
    @ApiOperation("保存或修改文章")
    public Object saveArticle(@RequestBody Article article) {
        return ResultUtil.success(articleService.saveOrUpdate(article));
    }

    @ApiOperation("文章分页列表")
    @GetMapping("page")
    public Object listArticles(PageVO pageVo) {
        if (pageVo.getPageNumber() == (null)) {
            pageVo.setPageNumber(1);
        }
        if (pageVo.getPageSize() == (null)) {
            pageVo.setPageSize(10);
        }
        IPage<Article> page = new Page<>(pageVo.getPageNumber(), pageVo.getPageSize());
        IPage<Article> pageList = articleMapper.selectPage(page, new QueryWrapper<Article>().orderByDesc("ORDER_NUM").orderByDesc("CREATE_TIME"));
        pageList.getRecords().forEach(article -> {
                    if (redisUtil.get(article.getId()) == null) {
                        redisUtil.incr(article.getId(), 1);
                        article.setViewCount(0L);
                    } else {
                        article.setViewCount(((Number) redisUtil.get(article.getId())).longValue());
                    }
                }
        );
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
    @GetMapping("article")
    public Object getArticle(String id) {
        Article article = articleMapper.selectById(id);
        if (article != null) {
            article.setViewCount(redisUtil.incr(id, 1));
            return ResultUtil.success(article);
        } else {
            return ResultUtil.error("未找到结果");
        }
    }

    @ApiOperation("根据id删除文章")
    @DeleteMapping("article")
    public Object removeArticle(@RequestBody Article article) {
        articleESDao.deleteById(article.getId());
        return ResultUtil.success(articleMapper.deleteById(article.getId()));
    }
}
