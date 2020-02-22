package com.leyou.cart.client;

import com.leyouo.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: <br>
 * @date 2020/2/2115:03
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
