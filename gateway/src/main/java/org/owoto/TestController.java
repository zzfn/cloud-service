package org.owoto;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzfn
 * @date 2020-12-30 5:33 下午
 */
@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping("v1")
    public Object getArticle(String id) {
        return "成功";
    }
}
