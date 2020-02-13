package com.leyou.page.page;

import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecificationClient;
import com.leyouo.item.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hx   <br>
 * @Title: <br>
 * @Package <br>
 * @Description: 查询商品详情页的数据 <br>
 * @date 2020/2/1221:48
 */
@Slf4j
@Service
public class PageService {

    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private SpecificationClient specClient;
    @Autowired
    private TemplateEngine templateEngine;

    public Map<String, Object> loadModel(Long spuId) {
        Map<String, Object> model = new HashMap<>();
        //查询spu
        Spu spu = goodsClient.querySpuById(spuId);
        //查询skus
        List<Sku> skus = spu.getSkus();
        //查询detail
        SpuDetail detail = spu.getSpuDetail();
        //查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        //查询商品分类
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));//查询商品的导航条（分类）
        //查询商品的规格
        List<SpecGroup> specs = specClient.queryListByCid(spu.getCid3());
        model.put("title", spu.getTitle());
        model.put("subTitle", spu.getSubTitle());
        model.put("skus", skus);
        model.put("detail", detail);
        model.put("brand", brand);
        model.put("categories", categories);
        model.put("specs", specs);
        return  model;
    }


    public void createHtml(Long spuId){
        //升成上下文
        Context context = new Context();
        context.setVariables(loadModel(spuId));
        //生成输出流
        File file = new File("E:\\ziji\\leyou2\\upload",spuId + ".html");
        try( PrintWriter write = new PrintWriter(file)) {
            //生成HTml
            templateEngine.process("item", context,write);

        }catch (Exception e){
             log.error("[item静态页服务] 生成静态页服务异常！",e);
        }

    }

}
