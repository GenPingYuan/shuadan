package com.exp.shuadan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.exp.shuadan.config.DataCheckException;
import com.exp.shuadan.config.ResponseModel;
import com.exp.shuadan.entity.product.Product;
import com.exp.shuadan.entity.product.ProductSeachModel;
import com.exp.shuadan.service.product.ProductService;
import com.exp.shuadan.util.UploadFileUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;

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
        return new ResponseModel(200, true, "", pageList);
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
        productService.addProduct(product);
        return resp;
    }

    @PostMapping(value = "deleteProduct")
    public ResponseModel delProduct(@RequestBody JSONObject params) throws Exception {
        ResponseModel resp = new ResponseModel<>();
        if (params == null || params.isEmpty()) {
            throw new DataCheckException(500, "请求参数为空");
        }
        log.info(params.toString());
        String ids = JSONObject.toJSONString(params.get("ids"));
        List<Integer> idList = JSON.parseObject(ids, new TypeReference<List<Integer>>() {
        });
        for (Integer id : idList) {
            // 获取产品信息
            Product product = productService.getProductById(id);
            productService.delProduct(id);
            // 删除图片
            String fileName = product.getImageUrl();
            String basePath = ResourceUtils.getURL("classpath:").getPath() + "static";
            File file = new File(basePath + fileName);
            if (file.exists()) {//文件是否存在
                file.delete();
            }
        }
        return resp;
    }

    @PostMapping(value = "updateProduct")
    public ResponseModel updateProduct(HttpServletRequest request, Product product, @RequestParam(value = "file", required = false) MultipartFile cardPic) throws Exception {
        ResponseModel resp = new ResponseModel<>();
        if (product == null || product.getId() == null) {
            throw new DataCheckException(500, "产品信息为空或者产品ID为空");
        }
        Product productOld = productService.getProductById(product.getId());
        // 判断是否有文件
        if (cardPic != null && !cardPic.isEmpty()) {
            // 上传新图片
            String imageUrl = uploadFileUtil.uploadFile(cardPic, null);
            product.setImageUrl(imageUrl);
        }
        product.setUpdateTime(new Date());
        productService.updateProduct(product);
        // 成功后把原图片删除
        if (cardPic != null && !cardPic.isEmpty()){
            String fileName = productOld.getImageUrl();
            String basePath = ResourceUtils.getURL("classpath:").getPath() + "static";
            File fileOld = new File(basePath + fileName);
            if (fileOld.exists()) {//文件是否存在
                fileOld.delete();
            }
        }
        return resp;
    }
}
