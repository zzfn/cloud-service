package org.owoto.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzfn
 * @date 2020-12-30 21:57
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "ignore")
@Getter
@Setter
public class WhiteList {
    private List<String> whites = new ArrayList<>();
}
