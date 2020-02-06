package com.leyouo.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/3
 * Time:21:55
 * Sku表数据
 */
@Data
@Table(name = "tb_sku")
public class Sku {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    /**spu表Id*/
    private Long spuId;
    /**标题*/
    private  String title;
    /**图片*/
    private String images;
    /**价格*/
    private Long price;
    /**特有规格属性在spu属性模板中的对应下标组合*/
    private String indexes;
    /**sku的特有规格参数键值对，json格式，反序列化时请使用linkedHashMap，保证有序*/
    private String own_spec;
    /**是否有效，0无效，1有效*/
    private Boolean enable;
    /**添加时间*/
    private Date createTime;
    /**最后修改时间*/
    private Date lastUpdateTime;

    /**库存,与数据库没有关系*/
    @Transient
    private  Integer stock;

}
