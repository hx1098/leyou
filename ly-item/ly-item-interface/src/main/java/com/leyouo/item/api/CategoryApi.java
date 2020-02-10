package com.leyouo.item.api;

import com.leyouo.item.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/10
 * Time:11:09
 * 商品分类api，为其他服务提供调用提供接口
 */

public interface CategoryApi {
    /**
     * 根据ids查询商品分类
     *
     * @param ids
     * @return
     */
    @GetMapping("category/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
