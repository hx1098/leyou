package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/10
 * Time:22:58
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    /**价格不能为空*/
    PRICE_CANNOT_BE_NULL(400,"价格不能为空！"),
    CATEGORY_NOT_FOND(404,"商品分类没有查到！"),
    ;
    private int code;
    private String msg;
}
