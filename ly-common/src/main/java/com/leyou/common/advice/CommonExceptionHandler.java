package com.leyou.common.advice;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/10
 * Time:22:39
 * 通用异常处理
 */
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(LyExcetion.class)
    public ResponseEntity<ExceptionResult> handleException(LyExcetion e){
        ExceptionEnum em = e.getExceptionEnum();
        return  ResponseEntity.status(e.getExceptionEnum().getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}
