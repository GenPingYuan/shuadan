package com.exp.shuadan.service.product;

import com.exp.shuadan.entity.product.Product;
import com.exp.shuadan.entity.product.ProductSeachModel;
import com.exp.shuadan.mapper.ProductMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public PageInfo<Product> getProduct(ProductSeachModel model) throws Exception{
        int pageNum = model.getPageNum();
        int pageSize = model.getPageSize();
        PageHelper.startPage(pageNum, pageSize,"create_time desc");
        List<Product> list = productMapper.getProduct(model);
        return new PageInfo<>(list);
    }

    public Product getProductById(int id)throws Exception{
        return productMapper.getProductById(id);
    }

    public void addProduct(Product product) throws Exception{
        productMapper.addProduct(product);
    }

    public void delProduct(int id)throws Exception{
        productMapper.delProduct(id);
    }

    public void updateProduct(Product product) throws Exception{
        productMapper.updateProduct(product);
    }
}
