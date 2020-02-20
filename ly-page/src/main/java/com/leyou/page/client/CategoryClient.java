package com.leyou.page.client;

import com.leyouo.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/9
 * Time:15:24
 * 商品分类client
 */

@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
