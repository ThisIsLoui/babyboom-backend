package com.babyboombackend.handler;

import com.babyboombackend.exception.BaseException;
import com.babyboombackend.vo.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数非法校验
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Result<String> handleMethodArgumentNotValidException(Exception ex) {
        try {
            String message = "参数校验失败";
            if(ex instanceof MethodArgumentNotValidException newEx){
                List<ObjectError> errors = newEx.getBindingResult().getAllErrors();
                message = errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            }else if(ex instanceof ConstraintViolationException newEx){
                message = (String)newEx.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            }
            log.error("参数校验失败: {}", message);
            return Result.error(message);
        } catch (Exception e) {
            return Result.error("参数校验失败");
        }
    }

    /**
     * 捕获请求参数格式不正确异常
     * @param ex
     * @return
     */
    @ExceptionHandler({UnexpectedTypeException.class, HttpMessageNotReadableException.class})
    public Result<String> handleUnexpectedTypeException(Exception ex )
    {
        log.error("参数格式不正确: \n{}",ex.getMessage());
        return Result.error("参数格式不正确");
    }

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result<String> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
