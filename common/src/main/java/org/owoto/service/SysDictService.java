package org.owoto.service;

/**
 * DICT(SysDict)表服务接口
 *
 * @author nanaouyang
 * @since 2020-03-27 20:34:19
 */
public interface SysDictService {

    /**
     * @param typeCode 字典类型
     * @param code 字典值
     * @return 字典翻译后的中文
     */
    String translate(String typeCode, String code);
}