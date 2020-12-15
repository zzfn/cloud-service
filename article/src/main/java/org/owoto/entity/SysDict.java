package org.owoto.entity;

import lombok.Data;

/**
 * DICT(SysDict)实体类
 *
 * @author nanaouyang
 * @since 2020-03-27 20:34:17
 */
@Data
public class SysDict extends BaseEntity{
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