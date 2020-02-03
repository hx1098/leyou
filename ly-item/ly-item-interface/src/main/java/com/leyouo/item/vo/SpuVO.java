package com.leyouo.item.vo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/3
 * Time:14:02
 */
public class SpuVO {
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
        private Boolean valid;
        /**创建时间*/
        private Date createTime;
    }
