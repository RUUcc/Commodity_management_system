package org.example.commodity_management_system_jzs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类（核心业务）
 */
@Data
@TableName("tb_product")
public class Product {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "商品名称不能为空")
    private String name;

    private String category;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于 0")
    private BigDecimal price;

    @Min(value = 0, message = "库存不能为负数")
    private Integer stock;

    private String image;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
