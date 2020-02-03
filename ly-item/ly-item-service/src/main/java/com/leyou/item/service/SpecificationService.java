package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyouo.item.pojo.SpecGroup;
import com.leyouo.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/1
 * Time:15:45
 * 规格组处理类
 * 规格参数处理类
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

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

   /* public List<SpecParam> queryParamByGid(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        List<SpecParam> list = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(list)){
            throw new LyExcetion(ExceptionEnum.SPEC_PARAMS_NOT_FOND);
        }
        return list;
    }*/

    public void addParam(SpecParam specParam) {
        System.err.println(specParam);
        specParamMapper.insert(specParam);
    }

    public void updParam(SpecParam specParam) {
        specParamMapper.updateByPrimaryKey(specParam);
    }

    public void delParam(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }

    public List<SpecParam> queryParamList(Long gid, Long cid, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> list = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(list)){
            throw new LyExcetion(ExceptionEnum.SPEC_PARAMS_NOT_FOND);
        }
        return list;
    }
}
