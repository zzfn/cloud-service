package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author zzfn
 * @date 2020-12-31 11:32 上午
 */
@TableName(value = "SYS_DICT")
public class Dict extends BaseEntity {
    /**
     * 编码
     */
    private String code;
    /**
     * 字典名
     */
    private String name;
    /**
     * 类型code
     */
    private String typeCode;
}
