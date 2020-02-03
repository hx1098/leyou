package com.leyouo.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/3
 * Time:13:43
 * spu详细表实体
 */
@Data
@Table(name = "tb_spu_detail")
public class SpuDetail {
    /**对应的SPU的id*/
    @Id
    private Long spuId;
    /**商品描述*/
    private String description;
    /**商品特殊规格的名称及可选值模板*/
    private String specTemplate;
    /**商品的全局规格属性*/
    private String specifications;
    /**包装清单*/
    private String packingList;
    /**售后服务*/
    private String afterService;

}
