package org.owoto;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzfn
 * @date 2020-12-07 11:44 上午
 */
@RestController
@Api(tags="say")
public class TestController {
    @GetMapping("say")
    public Object say(){
        return "hhhh";
    }
}
