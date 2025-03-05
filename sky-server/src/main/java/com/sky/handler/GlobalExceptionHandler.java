package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        if (message != null && message.contains("Duplicate entry")) {
            // 直接内联正则提取（假设错误信息格式为：Duplicate entry '用户名' for key）
            Matcher matcher = Pattern.compile("Duplicate entry '(.*?)'").matcher(message);
            String username = matcher.find() ? matcher.group(1) : "该";
            return Result.error(username + "用户名已存在");
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR); // 其他约束错误
    }

}
