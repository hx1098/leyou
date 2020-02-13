package com.leyouo.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/1
 * Time:15:38
 * 规格组数据表
 */
@Table(name = "tb_spec_group")
@Data
public class SpecGroup {

    @Id
    @KeySql(useGeneratedKeys = true)  //允许JDBC支持自动生成主键，需要驱动兼容
    private Long id;
    /**商品分类id，一个分类下有多个规格组*/
    private Long cid;
    /**规格组的名称*/
    private String name;

    /**规格参数，一个组下面有多个规格参数*/
    @Transient
    private List<SpecParam> params;
}
