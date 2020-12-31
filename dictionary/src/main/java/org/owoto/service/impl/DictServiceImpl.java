package org.owoto.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.owoto.entity.Dict;
import org.owoto.entity.SysDict;
import org.owoto.mapper.DictMapper;
import org.owoto.service.DictService;
import org.owoto.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zzfn
 * @date 2020-12-31 11:43 上午
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Autowired
    RedisUtil redisUtil;

    @Override
    public String translate(String typeCode, String code) {
        List<SysDict> list = JSON.parseArray(JSON.toJSONString(redisUtil.get("dict::" + typeCode)), SysDict.class);
        final String[] name = {""};
        if(null!=list){
            list.forEach(sysDict -> {
                if(sysDict.getCode().equals(code)){
                    name[0] =sysDict.getName();
                }
            });
            return name[0];
        }else {
            return null;
        }
    }
}
