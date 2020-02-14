package com.leyou.page.mq;

import com.leyou.page.page.PageService;
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
 * @Description:用于监听是否有商品详情页的静态页，发送过来消息 <br>
 * @date 2020/2/1419:52
 */

@Component
public class ItemListener {

    @Autowired
    private PageService pageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.insert.queue",durable = "true"),
            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void listenInsertAndUpdate(Long spuId){
        if(spuId == null){
            return;
        }
        //不为空 创建静态页
        pageService.createHtml(spuId);

    }

    /**
     * 创建队列 page.item.delete.queue 并持久化
     * 创建交换机
     * @param spuId
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.delete.queue",durable = "true"),
            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void listenDelete(Long spuId){
        if(spuId == null){
            return;
        }
        //不为空对静态页进行删除
        pageService.deleteHtml(spuId);

    }

}
