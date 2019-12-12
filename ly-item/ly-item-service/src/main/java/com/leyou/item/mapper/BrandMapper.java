package com.leyou.item.mapper;

import com.leyouo.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2019/12/12
 * Time:12:45
 */
//通用mapper来简化开发：
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 插入中间表数据
     * @param cid
     * @param bid
     * @return
     */
    @Insert("INSERT INTO tb_category_brand (category_id,brand_id) values (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid")Long cid,@Param("bid")Long bid);
}
