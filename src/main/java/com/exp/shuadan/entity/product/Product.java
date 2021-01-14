package com.exp.shuadan.entity.product;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Product {
    private Integer id;

    @NotNull(message = "产品名称不能为空")
    private String name;

    @NotNull(message = "产品价格不能为空")
    @DecimalMin(value = "0")
    private BigDecimal price;

    private String imageUrl;

    @NotNull(message = "产品简介不能为空")
    private String profile;

    @NotNull(message = "产品详情不能为空")
    private String detail;

    @Min(value = 0)
    @NotNull(message = "产品数量不能为空")
    private int count;

    @NotNull(message = "产品所属地区不能为空")
    private String country;

    private Timestamp createTime;
    private Timestamp updateTime;
}
