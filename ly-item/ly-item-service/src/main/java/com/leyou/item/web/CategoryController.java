package com.leyou.item.web;

import com.leyou.item.service.CategoryService;
import com.leyouo.item.pojo.Brand;
import com.leyouo.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/12
 * Time:9:43
 * 商品类别
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点查询商品分类
     * @param pid
     * @return
     */
    @GetMapping(value = "list")
    public ResponseEntity<List<Category>> qureyCategoryListByPid(@RequestParam(value = "pid",defaultValue = "0")Long pid){
        return ResponseEntity.ok(categoryService.qureyCategoryListByPid(pid));
    }

    /**
     * 根据品牌查询商品分类
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public  ResponseEntity<List<Category>> queryCateGoryByBid(@PathVariable("bid")Long bid){
        return ResponseEntity.ok(categoryService.queryBrndByBid(bid));
    }


    @PostMapping
    public  ResponseEntity<Void> saveBrand(Category category){
       /* categoryService.savecategory(category);*/
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据ids查询商品分类
     * @param ids
     * @return
     */
    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids")List<Long> ids){
       return ResponseEntity.ok(categoryService.queryByIds(ids));
    }

}
