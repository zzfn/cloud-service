package org.owoto.filter;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.owoto.config.WhiteList;
import org.owoto.util.JwtTokenUtil;
import org.owoto.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.StringUtils.substringAfter;

/**
 * @author zzfn
 * @date 2020-12-30 4:29 下午
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter {
    private static final String NULLSTR = "";
    private static final String START = "*";
    @Autowired
    private WhiteList whiteList;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String url=request.getURI().getPath();
        if(whiteList.getWhites().contains(url)){
            return chain.filter(exchange);
        }
        HttpHeaders httpHeaders = request.getHeaders();
        String tokenHeader = httpHeaders.getFirst(JwtTokenUtil.TOKEN_HEADER);
        if (StringUtils.isNotEmpty(tokenHeader)&&tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            try {
                String token = tokenHeader.substring(JwtTokenUtil.TOKEN_PREFIX.length());
                if(JwtTokenUtil.getUserIdFromToken(token)==null){
                    return setUnauthorizedResponse(exchange,"登录过期");
                }
            } catch (Exception e) {
                return setUnauthorizedResponse(exchange,"请重新登录");
            }
        } else {
            return setUnauthorizedResponse(exchange,"请先登录");
        }
        return chain.filter(exchange);
    }
    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg)
    {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        log.error("[鉴权异常处理]请求路径:{},请求ip:{}", exchange.getRequest().getPath(),exchange.getRequest().getRemoteAddress().getHostName());

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(ResultUtil.error(msg)));
        }));
    }
    public static boolean isEmpty(Collection<?> coll)
    {
        return isNull(coll) || coll.isEmpty();
    }
    public static boolean   matches(String str, List<String> strs)
    {
        if (isEmpty(str) || isEmpty(strs))
        {
            return false;
        }
        for (String testStr : strs)
        {
            if (matches(str, testStr))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isNull(Object object)
    {
        return object == null;
    }
    public static boolean isEmpty(String str)
    {
        return isNull(str) || NULLSTR.equals(str.trim());
    }
    public static boolean isEmpty(Object[] objects)
    {
        return isNull(objects) || (objects.length == 0);
    }
    /**
     * 查找指定字符串是否匹配指定字符串数组中的任意一个字符串
     *
     * @param str 指定字符串
     * @param strs 需要检查的字符串数组
     * @return 是否匹配
     */
    public static boolean matches(String str, String... strs)
    {
        if (isEmpty(str) || isEmpty(strs))
        {
            return false;
        }
        for (String testStr : strs)
        {
            if (matches(str, testStr))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找指定字符串是否匹配
     *
     * @param str 指定字符串
     * @param pattern 需要检查的字符串
     * @return 是否匹配
     */
    public static boolean matches(String str, String pattern)
    {
        if (isEmpty(pattern) || isEmpty(str))
        {
            return false;
        }

        pattern = pattern.replaceAll("\\s*", ""); // 替换空格
        int beginOffset = 0; // pattern截取开始位置
        int formerStarOffset = -1; // 前星号的偏移位置
        int latterStarOffset = -1; // 后星号的偏移位置

        String remainingURI = str;
        String prefixPattern = "";
        String suffixPattern = "";

        boolean result = false;
        do
        {
            formerStarOffset = StringUtils.indexOf(pattern, START, beginOffset);
            prefixPattern = substring(pattern, beginOffset, formerStarOffset > -1 ? formerStarOffset : pattern.length());

            // 匹配前缀Pattern
            result = remainingURI.contains(prefixPattern);
            // 已经没有星号，直接返回
            if (formerStarOffset == -1)
            {
                return result;
            }

            // 匹配失败，直接返回
            if (!result)
                return false;

            if (!isEmpty(prefixPattern))
            {
                remainingURI = substringAfter(str, prefixPattern);
            }

            // 匹配后缀Pattern
            latterStarOffset = StringUtils.indexOf(pattern, START, formerStarOffset + 1);
            suffixPattern = substring(pattern, formerStarOffset + 1, latterStarOffset > -1 ? latterStarOffset : pattern.length());

            result = remainingURI.contains(suffixPattern);
            // 匹配失败，直接返回
            if (!result)
                return false;

            if (!isEmpty(suffixPattern))
            {
                remainingURI = substringAfter(str, suffixPattern);
            }

            // 移动指针
            beginOffset = latterStarOffset + 1;

        }
        while (!isEmpty(suffixPattern) && !isEmpty(remainingURI));

        return true;
    }
}
