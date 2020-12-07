package org.owoto.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzfn
 * @date 2020-12-07 11:44 上午
 */
@RestController
@Api("测试")
public class TestController {
    @GetMapping("test")
    public Object test(){
        return "hhhh";
    }
}
