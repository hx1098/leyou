package com.leyou.search.repository;

import com.leyou.common.vo.PageResult;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.service.SearchService;
import com.leyouo.item.pojo.Spu;
import org.hibernate.validator.internal.engine.messageinterpolation.ElTermResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    /**
     * 先创建Goods的索引，再添加映射，此处的是一次性的东西
     */
    @Test
    public void InsertGoodsIndexTest(){
        esTemplate.createIndex(Goods.class);
        esTemplate.putMapping(Goods.class);
    }

    @Test
    public void loadData(){
        int page = 1;
        int rows = 100;
        int size = 0;
       do {
           //查询spu信息
           PageResult<Spu> result = goodsClient.querySpuByPage(page, rows, true, null);
           List<Spu> spuList = result.getItems();
           if (CollectionUtils.isEmpty(spuList)){
               break;
           }
           //构建Goods
           for (Spu spu : spuList) {
               Goods goods = searchService.buildGoods(spu);
           }
          // List<Goods> goodsList = spuList.stream().map(searchService::buildGoods).collect(Collectors.toList());
           //将goods存入到索引库
          // goodsRepository.saveAll(goodsList);
           //翻页
           page++;
           size = spuList.size();
       }while (size == 100);

    }

}
