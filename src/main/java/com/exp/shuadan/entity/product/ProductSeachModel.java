package com.exp.shuadan.entity.product;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class ProductSeachModel {
    @NotNull(message = "分页参数页码不能为空")
    private Integer pageNum;
    @NotNull(message = "分页参数页大小不能为空")
    private Integer pageSize;
    private String name;
    private BigDecimal startPrice;
    private BigDecimal endPrice;
    private String country;
    private String buyType;
    private Timestamp startTime;
    private Timestamp endTime;
}
