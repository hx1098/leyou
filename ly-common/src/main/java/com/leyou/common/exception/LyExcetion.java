package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/10
 * Time:22:56
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LyExcetion extends  RuntimeException{
   private ExceptionEnum exceptionEnum;
}
