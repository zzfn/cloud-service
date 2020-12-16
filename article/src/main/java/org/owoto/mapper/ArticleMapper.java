package org.owoto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.owoto.entity.Article;
import org.owoto.vo.Tags;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    Article getArticle(String id);

    List<Tags> getTags();

    List<Article> getArchives(String code);
}
