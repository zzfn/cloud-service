package org.owoto.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.owoto.entity.Dict;
import org.owoto.mapper.DictMapper;
import org.owoto.service.DictService;
import org.springframework.stereotype.Service;

/**
 * @author zzfn
 * @date 2020-12-31 11:43 上午
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
}
