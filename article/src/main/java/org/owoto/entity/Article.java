package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author zzfn
 * @date 2020-12-07 23:56
 */
@Data
public class Article extends BaseEntity{
    /**
     * title
     */
    private String title;

    /**
     * content
     */
    private String content;


    @TableField(exist = false)
    private Integer viewCount;


    private Integer orderNum;

    private String tag;

    @TableField(exist = false)
    private String tagDesc;
}
