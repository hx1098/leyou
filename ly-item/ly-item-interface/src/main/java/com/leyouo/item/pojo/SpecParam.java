package com.leyouo.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/2
 * Time:16:25
 * 规格参数实体表
 */
@Table(name = "tb_spec_param")
@Data
public class SpecParam {
    /**id*/
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    /**商品分类id*/
    private Long cid;
    /**商品组id*/
    private Long groupId;
    /**参数名*/
    private String name;
    /**是否是数字类型参数，true或false,此处的引号应该为到引号*/
    @Column(name = "`numeric`")
    private Boolean numeric;
    /**数字类型参数的单位，非数字类型可以为空*/
    private String unit;
    /**否是sku通用属性，true或false*/
    private Boolean generic;
    /**是否用于搜索过滤，true或false*/
    private Boolean searching;
    /**数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔：0.5-1.0*/
    private String segments;







}
