package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyouo.item.pojo.SpecGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/1
 * Time:15:45
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        List<SpecGroup> select = specGroupMapper.select(group);
        //没查到
        if (CollectionUtils.isEmpty(select)){
            throw new LyExcetion(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        return  select;
    }

    public void addSpec(SpecGroup specGroup) {
        specGroupMapper.insert(specGroup);
    }

    public void updSpec(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKey(specGroup);
    }

    public void delSpec(String id) {
        specGroupMapper.deleteByPrimaryKey(id);
    }
}
