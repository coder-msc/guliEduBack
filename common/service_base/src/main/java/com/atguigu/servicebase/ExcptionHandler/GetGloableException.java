package com.atguigu.servicebase.ExcptionHandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j   //将错误写入日志文件中的 注解
public class GetGloableException {

//    全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R getGloabelException(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

//    特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R getGloabelException(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了特定的异常处理，除数为0");
    }

//    自定义异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R getError(GuliException e){
        log.error(e.getMsg()); //可以将错误信息写入到日志文件中去
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
