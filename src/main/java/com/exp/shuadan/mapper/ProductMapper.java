package com.exp.shuadan.mapper;

import com.exp.shuadan.entity.product.Product;
import com.exp.shuadan.entity.product.ProductSeachModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    // 获取产品列表
    List<Product> getProduct(ProductSeachModel model) throws Exception;
    // 添加产品
    void addProduct(Product product) throws Exception;
    // 删除产品
    void delProduct(int id)throws Exception;
    // 编辑产品
    void updateProduct(Product product) throws Exception;
}
