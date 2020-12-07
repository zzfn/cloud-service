package org.owoto.controller;

import io.swagger.annotations.ApiOperation;
import org.owoto.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzfn
 * @date 2020-12-08 0:02
 */
@RestController
public class ArticleController {
    @Autowired
    private ArticleDao articleDao;
    @GetMapping("list")
    @ApiOperation("保存或修改文章")
    public Object list() {
        return articleDao.selectList(null);
    }
}
