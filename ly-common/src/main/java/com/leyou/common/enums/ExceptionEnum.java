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
    /**品牌商品关系表保存失败*/
    CATEGORY_BRAND_SAVE_ERROR(500,"品牌商品关系表保存失败"),
    /**品牌保存失败*/
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    /**价格不能为空*/
    PRICE_CANNOT_BE_NULL(400,"价格不能为空！"),
    /**商品分类没有查到*/
    CATEGORY_NOT_FOND(404,"商品分类没有查到！"),
    /**品牌分类没有查到*/
    BRAND_NOT_FOND(404,"品牌分类没有查到！"),
    ;
    private int code;
    private String msg;
}
