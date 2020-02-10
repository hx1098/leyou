package com.leyouo.item.api;

import com.leyouo.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/10
 * Time:11:16\
 * 规格参数api
 */
public interface SpecificationApi {
    /**
     * 根据组id查询参数
     * @param gid 组id
     * @param cid 类型id
     * @param searching 是否是搜索字段
     * @return
     */
    @GetMapping("spec/params")
   List<SpecParam> queryParamByList(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value = "cid",required = false)Long cid,
            @RequestParam(value = "searching",required = false)Boolean searching);
}
