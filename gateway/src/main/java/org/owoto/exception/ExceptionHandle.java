package org.owoto.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zzfn
 * @date 2020-12-30 4:20 下午
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handle(Exception e) {
        log.error("异常:{}", e);
//        if (e instanceof BusinessException) {
//            BusinessException businessException = (BusinessException) e;
//            return ResultUtil.error(businessException.getCode(), businessException.getMessage());
//        }
        return "ResultUtil.error(9999, e.getMessage())";
    }
}
