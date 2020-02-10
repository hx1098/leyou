package com.leyou.search.client;

import com.leyouo.item.api.GoodsApi;
import com.leyouo.item.pojo.Sku;
import com.leyouo.item.pojo.Spu;
import com.leyouo.item.pojo.SpuDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/10
 * Time:10:43
 * 商品api接口
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {


}
