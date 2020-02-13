package com.leyou.page.web;

import com.leyou.page.page.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: 用于向前段返回数据和themilf模板<br>
 * @date 2020/2/1220:27
 */
@Controller
public class PageController {

    @Autowired
    private PageService pageService;

    @GetMapping("item/{id}.html")
    public  String toItemPage(@PathVariable("id")Long spuId, Model model){
        //分析： 1.cid1,cid2,cid3,  2. 标题，副标题，价格，商品的规格，3.商品的介绍，规格包装，售后，
        //region 分析接口
        //1. spu的信息（根据id查询）
        //2. spuDetail详情
        //3. spu下的所有sku，
        //4. 品牌
        //5. 查询商品的三级分类
        //6. 商品的规格参数，规格参数组
        //endregion
        //准备模型数据
        Map<String,Object> attributes = pageService.loadModel(spuId);


        model.addAllAttributes(attributes);
        return "item";
    }
}
