package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.item.mapper.CategoryMapper;
import com.leyouo.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/12
 * Time:9:40
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    public List<Category> qureyCategoryListByPid(Long pid) {
        //查询条件mapper会把对象中的非空对象作为查询条件
        Category category = new Category();
        category.setParentId(pid);
        List<Category> select = categoryMapper.select(category);
        //判断查询条件
        if(CollectionUtils.isEmpty(select)){
             throw new LyExcetion(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        return select;
    }
}
