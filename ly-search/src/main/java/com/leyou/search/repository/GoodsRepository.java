package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/10
 * Time:13:29
 * 将spu，sku数据导入到索引库
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {

}
