package com.exp.shuadan.controller;

import com.alibaba.fastjson.JSON;
import com.exp.shuadan.config.DataCheckException;
import com.exp.shuadan.config.ResponseModel;
import com.exp.shuadan.entity.product.Product;
import com.exp.shuadan.entity.product.ProductSeachModel;
import com.exp.shuadan.service.product.ProductService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

@Validated
@RestController
@RequestMapping("/product/")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UploadFileUtil uploadFileUtil;

    @PostMapping(value = "getProduct")
    public ResponseModel getProduct(@Validated @RequestBody ProductSeachModel model) throws Exception {
        ResponseModel resp = new ResponseModel<>();
        PageInfo<Product> pageList = productService.getProduct(model);
    }

    @PostMapping(value = "addProduct")
    public ResponseModel addProduct(HttpServletRequest request, Product product, @RequestParam(value = "file") MultipartFile cardPic) throws Exception {
        ResponseModel resp = new ResponseModel<>();
        Date now = new Date();
        product.setCreateTime(now);
        product.setUpdateTime(now);
        log.info(JSON.toJSONString(product));
        String imageUrl = uploadFileUtil.uploadFile(cardPic, null);
        product.setImageUrl(imageUrl);
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        InputStream ins = file.getInputStream();
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = ins.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.flush();
        product.setImage(bos.toByteArray());
        productService.addProduct(product);
        return resp;
    }

    @DeleteMapping("{id}")
    public ResponseModel delProduct(@PathVariable(value = "id") Integer id) throws Exception {
        ResponseModel resp = new ResponseModel <>();
        productService.delProduct(id);
        return resp;
    }

    @PutMapping
    public ResponseModel updateProduct(@RequestBody Product product) throws Exception {
        ResponseModel resp = new ResponseModel <>();
        if (product == null || product.getId() == null) {
            throw new DataCheckException(500, "产品信息为空或者产品ID为空");
        }
<<<<<<< HEAD
        Product productOld = productService.getProductById(product.getId());
        // 判断是否有文件
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        if (!file.isEmpty()) {
            // 上传新图片
            String imageUrl = uploadFileUtil.uploadFile(file, null);
            product.setImageUrl(imageUrl);
        }
=======
>>>>>>> ca324d118f28c294f267c1d23611c6eb0682f669
        product.setUpdateTime(new Date());
        productService.updateProduct(product);
        return resp;
    }
}
