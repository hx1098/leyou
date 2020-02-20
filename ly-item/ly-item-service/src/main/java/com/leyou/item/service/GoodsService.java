package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyouo.item.pojo.*;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/3
 * Time:13:51
 * 商品服务
 */
@Service
public class GoodsService {

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public PageResult<Spu> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page, Math.min(rows, 100));//最多100条数据
        //过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //搜索字段过滤
        System.err.println("11111" + key);
        if (!StringUtils.isNotBlank(key)){
            criteria.andLike("title","%" + key + "%");
        }
        //上下架过滤
        if (saleable != null){
            criteria.orEqualTo("saleable",saleable);
        }
        //排序（根据商品更新时间排序）
        example.setOrderByClause("last_update_time DESC");
        //查询
        List<Spu> spus = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(spus)){
            throw new LyExcetion(ExceptionEnum.GOODS_NOT_FOND);
        }
        //解析分类和品牌的名称
        loadCategoryAndBrandName(spus);
        //解析分页结果
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);
        return  new PageResult<>(spuPageInfo.getTotal(),spus);
    }

    public void loadCategoryAndBrandName(List<Spu> spus){
        for (Spu spu : spus) {
            //处理分类名称
            List<String> names = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names,"/"));
            //处理品牌名称
            Brand brand = brandService.queryById(spu.getBrandId());
            spu.setBname(brand.getName());
        }
    }

    @Transactional
    public void saveGoods(Spu spu) {
        //新增spu数据
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(new Date());
        spu.setSaleable(true);
        spu.setValid(false);
        int count = spuMapper.insert(spu);
        if (count != 1){
            throw new LyExcetion(ExceptionEnum.GOODS_SAVE_ERROR);
        }
        //新增detail数据
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);
        saveSkuAndStock(spu, count);

        //发送mq消息
        amqpTemplate.convertAndSend("item.insert",spu.getId() );

    }

    private void saveSkuAndStock(Spu spu, int count) {
        //定义库存集合
        List<Stock> stockList = new ArrayList<>();

        //新增sku数据
        List<Sku> skus = spu.getSkus();
        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(new Date());
            sku.setSpuId(spu.getId());
            int skuCount = skuMapper.insert(sku);
            if (count != 1){
                throw new LyExcetion(ExceptionEnum.GOODS_SAVE_ERROR);
            }

            //新增stock
            Stock stock = new Stock();
            stock.setSku_id(sku.getId());//skuId
            stock.setStock(sku.getStock());//库存量
            stockList.add(stock);
        }
        //批量新增库存
        int stockCount = stockMapper.insertList(stockList);
        if (stockCount != stockList.size()){
            throw new LyExcetion(ExceptionEnum.GOODS_SAVE_ERROR);
        }
    }

    public SpuDetail queryDetailById(Long spuId) {
        SpuDetail detail = spuDetailMapper.selectByPrimaryKey(spuId);
        if (detail == null){
            throw new LyExcetion(ExceptionEnum.GOODS_DETAIL_NOT_FOND);
        }
        return detail;
    }

    public List<Sku> querySkuListBySpuId(Long spuId) {
        //1.查询sku数据
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skuList)){
            throw new LyExcetion(ExceptionEnum.GOODS_SKU_NOT_FOND);
        }
        //2.循环查询库存的数据
        for (Sku s : skuList) {
            Stock stock = stockMapper.selectByPrimaryKey(s.getId());
            if (stock == null){
                throw new LyExcetion(ExceptionEnum.GOODS_STOCK_NOT_FOND);
            }
            s.setStock(stock.getStock());//将查询出来的库存保存到skuList中
        }
        return skuList;
    }

    @Transactional
    public void updGoods(Spu spu) {
        if (spu.getId() == null){
            throw new LyExcetion(ExceptionEnum.GOODS_ID_NOT_FOND);
        }
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        //查询sku数据
        List<Sku> skuList = skuMapper.select(sku);
        if (!CollectionUtils.isEmpty(skuList)){
            //删除sku数据
            skuMapper.delete(sku);
            //删除stock数据
            List<Long> skuIdList = skuList.stream().map(Sku::getId).collect(Collectors.toList());
            stockMapper.deleteByIdList(skuIdList);
        }

        //修改spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(new Date());

        int spuCount = spuMapper.updateByPrimaryKeySelective(spu);
        if(spuCount != 1){
            throw new LyExcetion(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        //修改detail
        int count = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        //新增sku和stock
        saveSkuAndStock(spu,count);

        //发送mq消息
        amqpTemplate.convertAndSend("item.update",spu.getId() );

    }

    public Spu querySpuBySpuId(Long id) {
        //直接组合一下查询
        //查询出spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null){
            throw  new LyExcetion(ExceptionEnum.GOODS_NOT_FOND);
        }
        //查询sku
        List<Sku> skuList = querySkuListBySpuId(id);
        spu.setSkus(skuList);
        //查询detail
        spu.setSpuDetail(queryDetailById(id));
        return  spu;
    }
}
