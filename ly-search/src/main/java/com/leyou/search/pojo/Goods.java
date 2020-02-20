package com.leyou.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/9
 * Time:14:16
 */
@Data
@Document(indexName = "goods",type = "docs",shards = 1,replicas = 0)//索引库名称goods，索引库类型docs，分片数量为1，副本数量为0
public class Goods {
    /**spuid*/
    @Id
    private  Long id;
    /*所有被需要搜索的信息，包含标题，分类，设置品牌
     * 用来进行全文检索的字段，里面包含标题、商品分类信息
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")// FieldType.Text是可以分类，index：是否索引，store：是否存储，analyzer：分词器名称
    private String all;
    /**卖点*/
    @Field(type = FieldType.Keyword,index = false)
    private  String subTitle;
    /**品牌id*/
    private Long brandId;
    /**1级分类id*/
    private Long cid1;
    /**2级分类id*/
    private Long cid2;
    /**3级分类id*/
    private Long cid3;
    /**创建时间*/
    private Date createTime;
    /*价格
     * 价格数组，是所有sku的价格集合。方便根据价格进行筛选过滤
     */
    private Set<Long> price;// 价格，对应到elasticsearch/json中是数组，一个spu有多个sku，就有多个价格
    /*sku s的信息json结构
     * 用于页面展示的sku信息，不索引，不搜索。包含skuId、image、price、title字段
     */
    @Field(type = FieldType.Keyword,index = false)
    private String skus;
    /**可搜索的规格参数，key是参数名，值是参数值
     * 所有规格参数的集合。key是参数名，值是参数值
     *
     */
    private Map<String,Object> specs;



}
