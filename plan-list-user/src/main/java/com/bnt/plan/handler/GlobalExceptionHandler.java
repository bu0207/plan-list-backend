package com.bnt.plan.handler;

import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.common.ResultUtils;
import com.bnt.plan.exception.BusinessException;
import com.bnt.plan.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 自定义异常的handler
 * @author: kevin
 * @date: 2020/7/27 17:06
 * @version: V1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * ControllerAdvice有三种作用：全局异常处理、全局数据绑定、全局数据预处理
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler({Exception.class})
    public BaseResponse<String> jsonErrorHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResultUtils.error("系统异常");
    }

    /**
     * 没有访问权限。使用 @PreAuthorize 校验权限不通过时，就会抛出 AccessDeniedException 异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResponse handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return ResultUtils.error(HttpStatus.FORBIDDEN.value(), MessageUtils.message("user.no.permission"));
    }


    /**
     * 参数校验错误的异常处理
     *
     * @param e 参数校验错误异常
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String message = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));
        return ResultUtils.error(message);

    }

    /**
     * 业务异常处理
     *
     * @param e
     * @return ErrorInfo
     */
    @ExceptionHandler({BusinessException.class})
    public BaseResponse<String> businessExceptionHandler(BusinessException e) {
        log.error(e.getMessage(), e);
        return ResultUtils.error(e.getMessage());
    }
}
