package org.owoto.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.owoto.entity.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleDao extends BaseMapper<Article> {
}
