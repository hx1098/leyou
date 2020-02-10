package com.leyouo.item.api;

import com.leyou.common.vo.PageResult;
import com.leyouo.item.pojo.Sku;
import com.leyouo.item.pojo.Spu;
import com.leyouo.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/10
 * Time:10:53
 */
public interface GoodsApi {
    /**
     * 查询商品详情
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    SpuDetail queryDetailById(@PathVariable("id")Long spuId);


    /**
     * 查询商品sku表数据
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    List<Sku> querySkuListById(@RequestParam("id")Long spuId);

    /**
     * 商品查询
     * @param page 页码
     * @param rows 一页的数量
     * @param saleable 是否上下架
     * @param key 关键字
     * @return
     */
    @GetMapping("spu/page")
    PageResult<Spu> querySpuByPage(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "saleable",required = false)Boolean saleable,//可以不传此值
            @RequestParam(value = "key",defaultValue = "false")String key
    );
}