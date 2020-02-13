package com.leyou.page.client;

import com.leyouo.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/10
 * Time:11:18
 * 规格参数api接口
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
