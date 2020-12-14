package org.owoto.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.owoto.entity.Article;
import org.owoto.vo.Tags;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends BaseMapper<Article> {
    Article getArticle(String id);

    IPage<Article> listArticle(IPage<Article> page, @Param("title") String title);

    List<Tags> getTags();

    List<Article> getArchives(String code);
}
