package com.leyou.search.client;

import com.leyouo.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created with IDEA <br>
 * author:hanxiao <br>
 * Date:2020/2/10 <br>
 * Time:10:43 <br>
 * 商品api接口 <br>
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {


}
