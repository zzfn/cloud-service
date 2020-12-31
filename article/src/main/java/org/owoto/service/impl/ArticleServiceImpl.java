package org.owoto.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.owoto.entity.Article;
import org.owoto.mapper.ArticleMapper;
import org.owoto.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * @author zzfn
 * @date 2020-12-31 10:04 上午
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
