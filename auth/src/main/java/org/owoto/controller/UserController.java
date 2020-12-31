package org.owoto.controller;

import org.owoto.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzfn
 * @date 2020-12-31 1:15 下午
 */
@RestController
@RequestMapping("")
public class UserController {
    @PostMapping("white/login")
    public Object login(){
        return ResultUtil.success("yes");
    }
}
