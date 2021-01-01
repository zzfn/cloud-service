package org.owoto.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.owoto.entity.Article;
import org.owoto.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzfn
 * @date 2020-12-31 3:35 下午
 */
@RestController
@RequestMapping("/non/article")
@Slf4j
public class ArticleController {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("list")
    public Object getList(String keyword) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.should(QueryBuilders.matchPhraseQuery("title", keyword))
                .should(QueryBuilders.matchPhraseQuery("content", keyword))
                .should(QueryBuilders.matchPhraseQuery("tag_desc", keyword));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withHighlightBuilder(new HighlightBuilder().field("title").field("content").field("tag_desc")).build();
        List<Article> list = new ArrayList<>();
        SearchHits<Article> articleES = elasticsearchRestTemplate.search(nativeSearchQuery, Article.class);
        articleES.getSearchHits().forEach(searchHit -> {
            Article articleES1 = searchHit.getContent();
            articleES1.setContent(StringUtils.join(searchHit.getHighlightField("content"), " "));
            if (searchHit.getHighlightField("tagDesc").size() != 0) {
                articleES1.setTagDesc(StringUtils.join(searchHit.getHighlightField("tagDesc"), " "));
            }
            if (searchHit.getHighlightField("title").size() != 0) {
                articleES1.setTitle(StringUtils.join(searchHit.getHighlightField("title"), " "));
            }
            list.add(articleES1);
        });
        return ResultUtil.success(list);
    }

    @DeleteMapping("{id}")
    public Object deleteArticle(@PathVariable("id") String id) {
        return elasticsearchRestTemplate.delete(id, Article.class);
    }
}
