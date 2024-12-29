package com.eLearnPlayer.exception;

import javax.servlet.http.HttpServletRequest;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.eLearnPlayer.common.Result;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @create 2024-03-23 11:31
 * 控制器全局异常拦截
 */
@Order(999999)
@ControllerAdvice(basePackages = "com.eLearnPlayer.controller")
public class GlobalExceptionHandler {
    private static final Log log = LogFactory.get();
    //统一异常处理，主要用户Expectation
    @ExceptionHandler({Exception.class})
    @ResponseBody
    //返回json串
    public Result error(HttpServletRequest  request, Exception e){
        log.error("异常信息",e);
        return Result.error();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result customError(HttpServletRequest  request,CustomException e){
        log.error("异常信息",e.getMsg());
        System.out.println("捕获到了ClassCastException");
        return Result.error(e.getCode(),e.getMsg());
    }
}
