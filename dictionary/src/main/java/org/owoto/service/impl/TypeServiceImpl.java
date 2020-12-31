package org.owoto.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.owoto.entity.Type;
import org.owoto.mapper.TypeMapper;
import org.owoto.service.TypeService;
import org.springframework.stereotype.Service;

/**
 * @author zzfn
 * @date 2020-12-31 11:43 上午
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
}
