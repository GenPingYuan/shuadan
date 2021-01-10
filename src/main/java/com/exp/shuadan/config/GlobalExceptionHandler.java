package com.exp.shuadan.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 用来处理Exception异常
     *
     * @param e
     * @return ResponseModel
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseModel exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("请求url：{} 异常 : {}", request.getRequestURI(), e);
        // 用来处理DataCheckException 异常
        if ( e instanceof DataCheckException ) {
            DataCheckException dataCheckException = (DataCheckException) e;
            return new ResponseModel(dataCheckException.getCode(), false, dataCheckException.getMessage());
            // 用来处理bean validation异常
        } else if ( e instanceof ConstraintViolationException) {
            String message;
            ConstraintViolationException ex = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            if ( !CollectionUtils.isEmpty(constraintViolations) ) {
                StringBuilder msgBuilder = new StringBuilder();
                for (ConstraintViolation constraintViolation : constraintViolations) {
                    msgBuilder.append(constraintViolation.getMessage()).append(", ");
                }
                String errorMessage = msgBuilder.toString();
                if ( errorMessage.length() > 1 ) {
                    errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
                }
                message = "参数校验异常: " + errorMessage;
            } else {
                message = "参数校验异常";
            }
            return new ResponseModel(10001, false, message);

            // 用来处理method validation异常
        } else if ( e instanceof MethodArgumentNotValidException) {
            String message;
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
            if ( !CollectionUtils.isEmpty(objectErrors) ) {
                StringBuilder msgBuilder = new StringBuilder();
                for (ObjectError objectError : objectErrors) {
                    msgBuilder.append(objectError.getDefaultMessage()).append(", ");
                }
                String errorMessage = msgBuilder.toString();
                if ( errorMessage.length() > 1 ) {
                    errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
                }
                message =  "参数校验异常: " + errorMessage;
            } else {
                message = "参数校验异常";
            }
            return new ResponseModel(10001, false, message);
            // 处理未知异常
        } else {
            e.getMessage();
            return new ResponseModel(500,false, e.getMessage());
        }
    }
}