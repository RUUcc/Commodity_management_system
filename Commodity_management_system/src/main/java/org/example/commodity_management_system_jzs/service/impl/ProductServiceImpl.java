package org.example.commodity_management_system_jzs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.commodity_management_system_jzs.entity.Product;
import org.example.commodity_management_system_jzs.mapper.ProductMapper;
import org.example.commodity_management_system_jzs.service.ProductService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品 Service 实现
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    @Cacheable(value = "product", key = "#page.current + '_' + #page.size + '_' + #name + '_' + #category + '_' + #sortField + '_' + #sortOrder")
    public Page<Product> pageQuery(Page<Product> page, String name, String category,
                                   String sortField, String sortOrder) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();//条件构造器
        wrapper.like(StringUtils.hasText(name), Product::getName, name)
               .eq(StringUtils.hasText(category), Product::getCategory, category);

        // 动态排序
        boolean isAsc = "asc".equalsIgnoreCase(sortOrder);
        if ("id".equalsIgnoreCase(sortField)) {
            wrapper.orderBy(true, isAsc, Product::getId);
        } else if ("price".equalsIgnoreCase(sortField)) {
            wrapper.orderBy(true, isAsc, Product::getPrice);
        } else if ("stock".equalsIgnoreCase(sortField)) {
            wrapper.orderBy(true, isAsc, Product::getStock);
        } else {
            // 默认按创建时间降序
            wrapper.orderByDesc(Product::getCreateTime);
        }
        return this.page(page, wrapper);
    }

    @Override
    @Cacheable(value = "product", key = "'xml_' + #name + '_' + #category + '_' + #minPrice + '_' + #maxPrice")
    public List<Product> searchByCondition(String name, String category,
                                           BigDecimal minPrice, BigDecimal maxPrice) {
        return this.baseMapper.selectByCondition(name, category, minPrice, maxPrice);
    }

    @Override
    public int countByCategory(String category) {
        return this.baseMapper.countByCategory(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "product", allEntries = true)
    public boolean save(Product product) {
        return super.save(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "product", allEntries = true)
    public boolean updateById(Product product) {
        return super.updateById(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "product", allEntries = true)
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
