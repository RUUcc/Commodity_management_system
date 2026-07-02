package org.example.commodity_management_system_jzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.commodity_management_system_jzs.entity.Product;

import java.util.List;

/**
 * 商品 Mapper
 * - 继承 BaseMapper，自带 MyBatis-Plus CRUD
 * - 同时演示原生 MyBatis XML 和注解方式的自定义 SQL
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    // 继承 BaseMapper，自带 CRUD：insert / deleteById / updateById / selectById / selectList ...
    // ========== 注解方式（演示 MyBatis 原生注解） ==========

    /**
     * 按分类查询商品列表
     */
    @Select("SELECT * FROM tb_product WHERE category = #{category} ORDER BY create_time DESC")
    List<Product> selectByCategory(@Param("category") String category);

    /**
     * 更新商品库存
     */
    @Update("UPDATE tb_product SET stock = stock - #{count} WHERE id = #{id} AND stock >= #{count}")
    int reduceStock(@Param("id") Integer id, @Param("count") Integer count);

    // ========== XML 方式（方法实现在 mapper/ProductMapper.xml 中） ==========

    /**
     * 多条件组合查询（XML 版，配合动态 SQL）
     */
    List<Product> selectByCondition(@Param("name") String name,
                                    @Param("category") String category,
                                    @Param("minPrice") java.math.BigDecimal minPrice,
                                    @Param("maxPrice") java.math.BigDecimal maxPrice);

    /**
     * 查询商品总数（按分类统计）
     */
    int countByCategory(@Param("category") String category);
}
