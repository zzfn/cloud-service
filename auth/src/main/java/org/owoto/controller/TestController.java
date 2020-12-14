package org.owoto.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zzfn
 * @date 2020-12-07 11:44 上午
 */
@RestController
@Api(tags="测试")
public class TestController {
    @Autowired
    private Environment environment;
    @GetMapping("test")
    public Object test(){
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException ignored) {

        }
        assert localHost != null;
        String ip = localHost.getHostAddress();
        return ip;
    }
}
