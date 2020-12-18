package org.owoto.controller;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.owoto.entity.ArticleES;
import org.owoto.mapper.ArticleESDao;
import org.owoto.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zzfn
 * @date 2020-12-10 14:58
 */
@RestController
@RequestMapping("es")
@Slf4j
public class ArticleESController {
    @Autowired
    private ArticleESDao articleESDao;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostMapping("test")
    public Object listTags(@RequestBody ArticleES articleES) {
        return ResultUtil.success(articleESDao.save(articleES));
    }
    @PostMapping("del")
    public Object del() {
        articleESDao.deleteAll();
        elasticsearchRestTemplate.deleteIndex("article");
        return ResultUtil.success(null);
    }
    @GetMapping("list")
    public Object getList(String keyword) {
        BoolQueryBuilder queryBuilder= QueryBuilders.boolQuery();
        queryBuilder.should(QueryBuilders.matchPhraseQuery("title",keyword))
                .should(QueryBuilders.matchPhraseQuery("content",keyword))
                .should(QueryBuilders.matchPhraseQuery("tag_desc",keyword));
        NativeSearchQuery nativeSearchQuery=new NativeSearchQueryBuilder().withQuery(queryBuilder).withHighlightBuilder(new HighlightBuilder().field("title").field("content").field("tag_desc")).build();
//        Iterable<ArticleES> pages=articleESDao.search(nativeSearchQuery);
//        Iterable<ArticleES> pages=articleESDao.search(queryBuilder);
        List<Map> list=new ArrayList<>();
       SearchHits<ArticleES> articleES= elasticsearchRestTemplate.search(nativeSearchQuery,ArticleES.class);
       articleES.forEach(articleESSearchHit -> {
           list.add(articleESSearchHit.getHighlightFields());
       });
        return ResultUtil.success(list);
    }
}
