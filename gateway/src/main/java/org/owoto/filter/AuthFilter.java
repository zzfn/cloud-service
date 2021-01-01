package org.owoto.filter;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.owoto.config.Auth;
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

import java.util.regex.Pattern;

import static com.alibaba.nacos.common.utils.HttpMethod.OPTIONS;

/**
 * @author zzfn
 * @date 2020-12-30 4:29 下午
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter {
    @Autowired
    private Auth auth;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if(request.getMethod().matches(OPTIONS)){
            return chain.filter(exchange);
        }
        String url = request.getURI().getPath();
        if (Pattern.matches(auth.getNon(), url)) {
            return chain.filter(exchange);
        }
        HttpHeaders httpHeaders = request.getHeaders();
        String tokenHeader = httpHeaders.getFirst(JwtTokenUtil.TOKEN_HEADER);
        if (StringUtils.isNotEmpty(tokenHeader) && tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            try {
                String token = tokenHeader.substring(JwtTokenUtil.TOKEN_PREFIX.length());
                if (JwtTokenUtil.getUserIdFromToken(token) == null) {
                    return setUnauthorizedResponse(exchange, "登录过期");
                }
            } catch (Exception e) {
                return setUnauthorizedResponse(exchange, "请重新登录");
            }
        } else {
            return setUnauthorizedResponse(exchange, "请先登录");
        }
        return chain.filter(exchange);
    }

    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        log.error("[鉴权异常处理]请求路径:{},请求ip:{}", exchange.getRequest().getPath(), exchange.getRequest().getRemoteAddress().getHostName());

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(ResultUtil.error(msg)));
        }));
    }
}
