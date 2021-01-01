package org.owoto.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zzfn
 * @date 2020-12-31 4:33 下午
 */
@Component
@FeignClient(name = "search")
public interface SearchService {
    @GetMapping("white/article/{id}")
    String deleteById(@PathVariable("id")String id);
}
