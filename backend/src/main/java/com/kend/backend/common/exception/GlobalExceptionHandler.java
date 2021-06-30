package com.kend.backend.common.exception;


import com.baomidou.mybatisplus.extension.api.R;
import com.kend.backend.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// 捕获controller 异常
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 设置状态码
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value= RuntimeException.class)
    public Result handler( RuntimeException e){
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalAccessException.class)
    public Result handler(IllegalAccessException e){
        log.error("Assert 异常: --------- {}",e.getMessage());
        return Result.fail(e.getMessage());
    }


    // 处理实体校验异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        ObjectError objectError =result.getAllErrors().stream().findFirst().get();
        log.error("实体校验异常: --------- {}",objectError.getDefaultMessage());

        return Result.fail(objectError.getDefaultMessage());
    }
}
