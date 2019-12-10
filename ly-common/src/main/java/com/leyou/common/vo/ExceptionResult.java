package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/10
 * Time:23:13
 * 封装异常处理对象
 */
@Data
public class ExceptionResult {
    private  int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em){
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
