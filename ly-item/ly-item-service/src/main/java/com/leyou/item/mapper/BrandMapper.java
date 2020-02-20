package com.leyou.item.mapper;

import com.leyouo.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

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

    /**
     * 品牌表和品牌分类中间表 联合 根据categoryId查询品牌表数据
     * @param cid
     * @return
     */
    @Select("SELECT b.* from tb_brand  b" +
            " inner join tb_category_brand c on b.id = c.brand_id " +
            " where c.category_id = #{cid}")
    List<Brand> queryBrandByCid(@Param("cid") Long cid);
}
