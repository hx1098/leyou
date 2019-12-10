package com.leyou.item.service;

import com.leyouo.item.pojo.Item;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/10
 * Time:21:49
 */
@Service
public class ItemService {
    public Item saveItem(Item item){
        //为实现商品新增
        int id = new Random().nextInt();
        item.setId(id);
        return  item;
    }
}
