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
    /**无效的文件类型*/
    FILE_TYPE_NOT_ERROR(500,"无效的文件类型"),
    /**文件上传失败*/
    UPLOAD_ERROR(500,"文件上传失败！"),
    /**品牌商品关系表保存失败*/
    CATEGORY_BRAND_SAVE_ERROR(500,"品牌商品关系表保存失败"),
    /**品牌保存失败*/
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    /**价格不能为空*/
    PRICE_CANNOT_BE_NULL(400,"价格不能为空！"),
    /**商品分类没有查到*/
    CATEGORY_NOT_FOND(404,"商品分类没有查到！"),
    /**商品分类保存失败*/
    CATEGORY_SAVE_ERROR(404,"商品分类保存失败！"),
    /**品牌分类没有查到*/
    BRAND_NOT_FOND(404,"品牌分类没有查到！"),
    /**规格组没有查到*/
    SPEC_GROUP_NOT_FOND(404,"规格组没有查到！"),
    /**规格参数没有查到*/
    SPEC_PARAMS_NOT_FOND(404,"规格参数没有查到！"),
    /**商品没有查到*/
    GOODS_NOT_FOND(404,"商品没有查到！"),
    ;
    private int code;
    private String msg;
}
