package com.leyou.search.client;

import com.leyouo.item.api.CategoryApi;
import com.leyouo.item.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
