package org.owoto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.owoto.entity.Dict;

/**
 * @author zzfn
 * @date 2020-12-31 11:45 上午
 */
public interface DictService extends IService<Dict> {
    /**
     * @param typeCode 字典类型
     * @param code 字典值
     * @return 字典翻译后的中文
     */
    String translate(@Param("typeCode") String typeCode, @Param("code") String code);
}
