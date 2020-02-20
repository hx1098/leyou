package com.leyou.item.mapper;

import com.leyouo.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/12
 * Time:9:38
 */
public interface CategoryMapper  extends Mapper<Category>, SelectByIdListMapper<Category,Long> {

    @Select("SELECT * FROM tb_category WHERE ID IN " +
            "(" +
            "SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid}" +
            ")")
    List<Category> queryBrndBycid(Long bid);

}
