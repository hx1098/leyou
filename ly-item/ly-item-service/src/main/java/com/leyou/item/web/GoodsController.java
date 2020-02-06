package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.service.GoodsService;
import com.leyouo.item.pojo.Sku;
import com.leyouo.item.pojo.Spu;
import com.leyouo.item.pojo.SpuDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/3
 * Time:13:52
 * 商品处理类
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品查询
     * @param page 页码
     * @param rows 一页的数量
     * @param saleable 是否上下架
     * @param key 关键字
     * @return
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "saleable",required = false)Boolean saleable,//可以不传此值
            @RequestParam(value = "key",defaultValue = "false")String key
    ){
        return  ResponseEntity.ok(goodsService.querySpuByPage(page,rows,saleable,key));
    }

    /**
     * 商品新增
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<String> saveGoods(@RequestBody Spu spu){
        goodsService.saveGoods(spu);
        return  ResponseEntity.status(HttpStatus.CREATED).body("Created Success");
    }
    //region
    //endregion


    /**
     * 查询商品详情
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> queryDetailById(@PathVariable("id")Long spuId){
        return ResponseEntity.ok(goodsService.queryDetailById(spuId));
    }

    /**
     * 查询商品sku表数据
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuListById(@RequestParam("id")Long spuId){
        return ResponseEntity.ok(goodsService.querySkuListBySpuId(spuId));
    }
    /**
     * 商品新增
     * @param spu
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<String> updGoods(@RequestBody Spu spu){
        goodsService.updGoods(spu);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body("Created Success");
    }

}
