package org.example.commodity_management_system_jzs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.commodity_management_system_jzs.entity.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品 Service 接口
 */
public interface ProductService extends IService<Product> {

    /**
     * 分页 + 多条件查询（MyBatis-Plus 条件构造器版本），支持动态排序
     *
     * @param sortField 排序字段：id / price / stock，为 null 时按创建时间降序
     * @param sortOrder 排序方向：asc / desc
     */
    Page<Product> pageQuery(Page<Product> page, String name, String category,
                            String sortField, String sortOrder);

    /**
     * 多条件组合查询（原生 MyBatis XML 版本，演示动态 SQL）
     */
    List<Product> searchByCondition(String name, String category, BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * 按分类统计数量（演示 XML 查询）
     */
    int countByCategory(String category);
}
