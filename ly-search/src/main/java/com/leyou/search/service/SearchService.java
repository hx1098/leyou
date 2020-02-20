package com.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.common.utils.JsonUtils;

import com.leyou.common.utils.NumberUtils;
import com.leyou.common.vo.PageResult;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecificationClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.repository.GoodsRepository;
import com.leyouo.item.pojo.*;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.xml.bind.ValidationEvent;
import java.awt.print.PrinterAbortException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/10
 * Time:14:13
 * 搜索服务，目的是将查询的数据对象封装成一个goods对象
 */
@Service
public class SearchService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specClient;

    @Autowired
    private GoodsRepository goodsRepository;

    /**
     * 根据一个spu对象将数据封装成一个Goods对象(Goods对象是一个可以直接放入到搜索中去)
     * @param spu
     * @return
     */
    public Goods buildGoods(Spu spu){
        Long spuId = spu.getId();
        //构建goods对象
        Goods goods = new Goods();
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setId(spu.getId());
        goods.setSubTitle(spu.getSubTitle());

        //////////////////////查询分类
        List<Category> categories = categoryClient.queryCategoryByIds(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if (CollectionUtils.isEmpty(categories)){
            throw new LyExcetion(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());//获取分类名称集合,  /手机/手机/iphone8

        ///////////////////查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        if (brand == null){
              throw new LyExcetion(ExceptionEnum.BRAND_NOT_FOND);
        }

        //----> goods.setAll(all);拼接搜索字段，title +  商品分类（name1,name2,name3） + 品牌名称（brandName）
        String all = spu.getTitle() + StringUtils.join(names," ") + brand.getName();

        //查sku的价格
        List<Sku> skuList = goodsClient.querySkuListById(spu.getId());
        if (CollectionUtils.isEmpty(skuList)){
            throw new LyExcetion(ExceptionEnum.GOODS_SKU_NOT_FOND);
        }
        //---->goods.setPrice(priceList); 商品的sku的价格
//        Set<Long> priceList = skuList.stream().map(Sku::getPrice).collect(Collectors.toSet());
        Set<Long> priceList = new HashSet<>();//避免多次进行循环
        //---->goods.setSkus(); 商品的skuList,这里需要进行处理，每一个sku中的数据都是全部，我们只需要id，title，price，images就行
        List<Map<String,Object>> skus = new ArrayList<>();
        for (Sku sku : skuList) { //将每一个sku的id,title,price,image数据一个一个循环取出来放到新的list中
            Map<String,Object> map = new HashMap<>();
            map.put("id", sku.getId());
            map.put("title", sku.getTitle());
            map.put("price", sku.getPrice());
            map.put("images", StringUtils.substringBefore(sku.getImages(),","));
            skus.add(map);

            //处理价格
            priceList.add(sku.getPrice());
        }


        //查询规格参数和查询商品详情
        List<SpecParam> specParams = specClient.queryParamByList(null, spu.getCid3(), true);
        if (CollectionUtils.isEmpty(specParams)){
            throw new LyExcetion(ExceptionEnum.SPEC_PARAMS_NOT_FOND);
        }
        SpuDetail spuDetail = goodsClient.queryDetailById(spuId);//获取商品详情
        //获取通用规格参数
        String genericSpecJson = spuDetail.getGenericSpec();
        Map<Long, String> genericSpec = JsonUtils.parseMap(genericSpecJson, Long.class, String.class);//将json转为map格式
        //获取特有规格参数
        String specialSpecJson = spuDetail.getSpecialSpec();
        Map<Long, List<String>> specialSpec = JsonUtils.nativeRead(specialSpecJson, new TypeReference<Map<Long, List<String>>>() {});//specialSpecJson中有list，使用这种方式转换为map
        //将specParams中的规格参数作为key，值genericSpec，值specialSpec与key进行拼接对应即可
        //spec规格参数(需要查询规格参数，和商品详情)
        Map<String,Object> specs = new HashMap<>();
        for (SpecParam param : specParams) {
            //规格名称为key，规格名称的值为value
            String key = param.getName();
            Object value = "";
            if(param.getGeneric()){//判断当前的属性值是否是通过属性
                value = genericSpec.get(param.getId());
                //判断是否是数值类型，如果是数值类型将他处理为屏幕尺寸的分类段，以便于后期的搜索
                if(param.getNumeric()){
                   value = chooseSegment(value.toString(), param);
                }
            }else{
                value = specialSpec.get(param.getId());
            }
            specs.put(key, value);
        }



        goods.setAll(all); // 搜索字段，包含标题，分类，品牌，规格
        goods.setPrice(priceList);//  所有的sku的价格集合
        goods.setSkus(JsonUtils.serialize(skus));//  所有sku 的集合的json格式
        goods.setSpecs(specs); // Todo 所有的可搜索的规格参数





        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        String[] split = p.getSegments().split(",");
        // 保存数值段
        for (String segment : split) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){//代表只有一个数据，填写以上
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){//开头为0的话就是以下，
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    /**
     * 查询功能
     * @param searchRequest
     *  GET goods/_search
     * {
     *
     *   "query": {
     *     "match": {
     *       "all": "手机 "
     *     }
     *   },
     *   "_source": ["id","subTitle","skus"]
     * }
     * @return
     */
    public PageResult<Goods> search(SearchRequest searchRequest) {
        int page = searchRequest.getPage()-1;
        int size = searchRequest.getSize();

        // 1创建查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //结果过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));//"_source": ["id","subTitle","skus"]
        // 2分页
        queryBuilder.withPageable(PageRequest.of(page, size));
        // 3过滤
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", searchRequest.getKey()));//all是要查询的字段的名字，key是进行匹配的字段
        //4查询
        Page<Goods> result = goodsRepository.search(queryBuilder.build());
        long total = result.getTotalElements();
        long totalPages = result.getTotalPages();
        List<Goods> goodsList = result.getContent();

        return new PageResult<>(total, totalPages, goodsList);
    }

    /**
     * 根据spuId更新创建索引
     * @param spuId
     */
    public void createOrUpdateIndex(Long spuId) {
        //查询spu
        Spu spu = goodsClient.querySpuById(spuId);
        //构建goods
        Goods goods = buildGoods(spu);
        //存入索引库看
        goodsRepository.save(goods);
    }

    /**
     * 根据spuID删除索引
     * @param spuId
     */
    public void deleteIndex(Long spuId) {
        goodsRepository.deleteById(spuId);
    }
}
