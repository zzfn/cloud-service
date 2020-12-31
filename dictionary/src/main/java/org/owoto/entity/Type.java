package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author zzfn
 * @date 2020-12-31 11:34 上午
 */
@TableName(value = "SYS_DICT_TYPE")
public class Type extends BaseEntity {
    /**
     * 编码
     */
    private String code;
    /**
     * 类型
     */
    private String name;
}
