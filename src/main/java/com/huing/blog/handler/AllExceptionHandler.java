package com.huing.blog.handler;

import com.huing.blog.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author huing
 * @create 2022-07-04 9:26
 */
//对加了@Controller注解的方法进行拦截处理 AOP的实现
@RestControllerAdvice
public class AllExceptionHandler {

    //进行异常处理，处理Except.class的异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e){
        e.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
