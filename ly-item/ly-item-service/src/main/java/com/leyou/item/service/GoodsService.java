package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyouo.item.pojo.Brand;
import com.leyouo.item.pojo.Category;
import com.leyouo.item.pojo.Spu;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
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
}
