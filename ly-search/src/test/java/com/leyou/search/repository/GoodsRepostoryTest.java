package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.hibernate.validator.internal.engine.messageinterpolation.ElTermResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/10
 * Time:13:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepostoryTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 先创建Goods的索引，再添加映射，此处的是一次性的东西
     */
    @Test
    public void InsertGoodsIndexTest(){
        esTemplate.createIndex(Goods.class);
        esTemplate.putMapping(Goods.class);

    }
}
