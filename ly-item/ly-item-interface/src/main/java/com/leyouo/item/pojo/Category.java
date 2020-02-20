package com.leyouo.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/12
 * Time:9:27
 * 商品类别id
 */
@Table(name = "tb_category")
@Data
public class Category {
    @Id
    @KeySql(useGeneratedKeys = true)  //允许JDBC支持自动生成主键，需要驱动兼容
    private  Long id;
    /**类别id*/
    private String name;
    /**父级id*/
    private Long parentId;
    /**是否是父级id*/
    private Boolean isParent;
    /**排序*/
    private Integer sort;
}
