package com.leyouo.item.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/3
 * Time:10:01
 * Spu数据表
 */
@Data
@Table(name = "tb_spu")
public class Spu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    /**品牌id*/
    private Long brandId;
    /**1级类目*/
    private Long cid1;
    /**2级类目*/
    private Long cid2;
    /**3级类目*/
    private Long cid3;
    /**标题*/
    private String title;
    /**子标题*/
    private String subTitle;
    /**是否上架*/
    private Boolean saleable;
    /**是否有效，逻辑删除用*/
    @JsonIgnore
    private Boolean valid;
    /**创建时间*/
    private Date createTime;
    /**最后修改时间*/
    @JsonIgnore
    private Date lastUpdateTime;

    /**category规格名称*/
    @Transient
    private String cname;
    /**品牌名称*/
    @Transient
    private String bname;

    /**spuDetail表数据*/
    @Transient
    private SpuDetail spuDetail;
    /**sku表数据*/
    @Transient
    private List<Sku> skus;


}
