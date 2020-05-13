package cn.sher6j.servicebase.exceptionhandler;

import cn.sher6j.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 * @author sher6j
 * @create 2020-05-13-21:20
 */
@ControllerAdvice
@Slf4j //向logback日志中写入东西的注解
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回json数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //特定异常，如果有特定异常，先执行特定异常，没有特定异常处理才会使用全局异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回json数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody //为了返回json数据
    public R error(GuliException e) {
        log.error(e.getMessage()); //将日志信息写到error.log中
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
