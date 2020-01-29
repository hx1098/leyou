package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyouo.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/12
 * Time:12:47
 * 品牌管理
 */
@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;


    public PageResult<Brand> queryBrandPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)){
            //过滤条件
              example.createCriteria().orLike("name","%" + key + "%")
                      .orEqualTo("letter",key.toUpperCase());
        }
        //排序
        if (StringUtils.isNotBlank(sortBy)){
            String OrderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(OrderByClause);
        }
        //查询
        List<Brand> brands = brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(brands)){
            throw  new LyExcetion(ExceptionEnum.BRAND_NOT_FOND);
        }
        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(brands);
        return new PageResult<>(info.getTotal(),brands);
    }

    /**
     * 新增品牌功能
     * @param brand  品牌数据
     * @param cids  商品类型，一个品牌可能有多个商品类型
     * @despringtion: 添加事物
     * @return
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增品牌
        brand.setId(null);
        int count = brandMapper.insert(brand);
        if (count != 1){
            throw  new LyExcetion(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        //新增中间表
        for(Long cid : cids){
            brandMapper.insertCategoryBrand(cid,brand.getId());
            if (count != 1){
                throw  new LyExcetion(ExceptionEnum.CATEGORY_BRAND_SAVE_ERROR);
            }
        }
    }


}
