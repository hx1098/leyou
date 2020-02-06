package com.leyouo.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/3
 * Time:22:05
 * 库存量表数据
 */
@Data
@Table(name = "tb_stock")
public class Stock {

    @Id
    private  Long sku_id;
    /**秒杀可用库存*/
    private Integer seckillStock;
    /**已秒杀数量*/
    private Integer seckillTotal;
    /**正常库存*/
    private Integer stock;
}
