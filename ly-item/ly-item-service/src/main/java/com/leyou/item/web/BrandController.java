package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.service.BrandService;
import com.leyouo.item.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/12
 * Time:12:48
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌数据
     * @param page  页面
     * @param rows  每页的数量
     * @param sortBy  根据哪个字段排序
     * @param desc  是否是倒序
     * @param key  关键字
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandPage(
      @RequestParam(value = "page",defaultValue = "1")Integer page,
      @RequestParam(value = "rows",defaultValue = "5")Integer rows,
      @RequestParam(value = "sortBy",required = false)String sortBy,//可以不传此值
      @RequestParam(value = "desc",defaultValue = "false")Boolean desc,
      @RequestParam(value = "key",required = false)String key
    ){
        PageResult<Brand> result = brandService.queryBrandPage(page,rows,sortBy,desc,key);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增品牌功能
     * @param brand  品牌数据
     * @param cids  商品类型，一个品牌可能有多个商品类型
     * @return
     */
    @PostMapping
    public  ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid")Long cid){
        return ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        return ResponseEntity.ok(brandService.queryById(id));
    }


}
