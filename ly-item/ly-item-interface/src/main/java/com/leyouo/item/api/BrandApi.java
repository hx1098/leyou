package com.leyouo.item.api;

import com.leyouo.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/10
 * Time:11:13
 * 品跑api,为其他服务提供接口数据
 */
public interface BrandApi {
    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("brand/{id}")
    Brand queryBrandById(@PathVariable("id")Long id);
}
