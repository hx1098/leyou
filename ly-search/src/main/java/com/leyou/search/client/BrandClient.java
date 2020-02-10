package com.leyou.search.client;

import com.leyouo.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/10
 * Time:11:15
 * 品牌api，专门调用其他接口
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {

}
