package com.leyou.item.web;

import com.leyou.item.service.CategoryService;
import com.leyouo.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Category>> qureyCategoryListByPid(@RequestParam("pid")Long pid){
        return ResponseEntity.ok(categoryService.qureyCategoryListByPid(pid));
    }
}
