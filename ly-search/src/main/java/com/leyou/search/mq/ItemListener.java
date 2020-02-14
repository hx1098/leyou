package com.leyou.search.mq;

import com.leyou.search.service.SearchService;
import com.leyouo.item.pojo.SpuDetail;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description:用于监听是否有商品进行更改，发送过来消息 <br>
 * @date 2020/2/1419:52
 */

@Component
public class ItemListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.insert.queue",durable = "true"),
            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void listenInsertAndUpdate(Long spuId){
        if(spuId == null){
            return;
        }
        //不为空 处理消息（对索引库进行新增或修改）
       searchService.createOrUpdateIndex(spuId);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.delete.queue",durable = "true"),
            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void listenDelete(Long spuId){
        if(spuId == null){
            return;
        }
        //不为空 处理消息（对索引库进行新增或修改）
        searchService.deleteIndex(spuId);

    }

}
