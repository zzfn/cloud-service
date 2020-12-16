package org.owoto.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import lombok.extern.slf4j.Slf4j;
import org.owoto.entity.SysDict;
import org.owoto.service.SysDictService;
import org.owoto.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DICT(SysDict)表服务实现类
 *
 * @author nanaouyang
 * @since 2020-03-27 20:34:20
 */
@Service
@Slf4j
public class SysDictServiceImpl implements SysDictService {
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