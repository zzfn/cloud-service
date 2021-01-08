package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.owoto.annotation.Dict;


/**
 * @author cc
 */
@Data
public class Article extends BaseEntity {

    /**
     * title
     */
    private String title;

    /**
     * content
     */
    private String content;


    @TableField(exist = false)
    private Long viewCount;


    private Integer orderNum;

    /**
     * 是否发布
     */
    private char isRelease;

    @Dict(target = "tagDesc",codeType = "TAG")
    private String tag;

    @TableField(exist = false)
    private String tagDesc;

}
