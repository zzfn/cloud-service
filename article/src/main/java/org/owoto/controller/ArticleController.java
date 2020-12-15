package org.owoto.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.owoto.dao.ArticleDao;
import org.owoto.dao.ArticleESDao;
import org.owoto.entity.Article;
import org.owoto.utils.ResultUtil;
import org.owoto.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzfn
 * @date 2020-12-08 0:02
 */
@RestController
@RequestMapping("article")
@Slf4j
public class ArticleController {
    @Autowired
    ArticleDao articleDao;

    @ApiOperation("文章分页列表")
    @GetMapping("listArticles")
    public Object listArticles(PageVO pageVo, String title) {
        if(pageVo.getPageNumber()==(null)){
            pageVo.setPageNumber(1);
        }
        if(pageVo.getPageSize()==(null)){
            pageVo.setPageSize(10);
        }
        log.error("{}",pageVo.getPageSize());
        IPage<Article> page = new Page<>(pageVo.getPageNumber(), pageVo.getPageSize());
        IPage<Article> pageList = articleDao.listArticle(page,title);
        return ResultUtil.success(pageList);
    }

    @ApiOperation("文章总数")
    @GetMapping("countArticles")
    public Object countArticles() {
        return ResultUtil.success(articleDao.selectCount(null));
    }

    @ApiOperation("文章分类")
    @GetMapping("listTags")
    public Object listTags() {
        return ResultUtil.success(articleDao.getTags());
    }

    @ApiOperation("文章列表不分页")
    @GetMapping("listArchives")
    public Object listArchives(String code) {
        return ResultUtil.success(articleDao.getArchives(code));
    }

    @ApiOperation("根据id查询文章详情")
    @GetMapping("getArticle")
    public Object getArticle(String id) {
        return ResultUtil.success(articleDao.getArticle(id));
    }

}
