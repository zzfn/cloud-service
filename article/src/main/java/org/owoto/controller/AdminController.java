package org.owoto.controller;

import io.swagger.annotations.ApiOperation;
import org.owoto.entity.Article;
import org.owoto.service.ArticleService;
import org.owoto.service.SearchService;
import org.owoto.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzfn
 * @date 2021-01-01 12:06
 */
@RestController
@RequestMapping("sys")
public class AdminController {
    @Autowired
    ArticleService articleService;
    @Autowired
    SearchService searchService;

    @PostMapping("article")
    @ApiOperation("保存或修改文章")
    public Object saveArticle(@RequestBody Article article) {
        return ResultUtil.success(articleService.saveOrUpdate(article));
    }

    @ApiOperation("根据id删除文章")
    @DeleteMapping("sys/article/{id}")
    public Object removeArticle(@PathVariable("id") String id) {
        searchService.deleteById(id);
        return ResultUtil.success(articleService.removeById(id));
    }
}
