package com.exp.shuadan.controller;

import com.exp.shuadan.config.DataCheckException;
import com.exp.shuadan.config.ResponseModel;
import com.exp.shuadan.entity.product.Product;
import com.exp.shuadan.entity.product.ProductSeachModel;
import com.exp.shuadan.service.product.ProductService;
import com.exp.shuadan.util.UploadFileUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UploadFileUtil uploadFileUtil;

    @GetMapping
    public ResponseModel getProduct(@Validated @RequestBody ProductSeachModel model) throws Exception {
        ResponseModel resp = new ResponseModel<>();
        PageInfo<Product> pageList = productService.getProduct(model);
        resp.setData(pageList);
        return resp;
    }

    @PostMapping
    public ResponseModel addProduct(HttpServletRequest request, @Validated @RequestBody Product product) throws Exception {
        ResponseModel resp = new ResponseModel<>();
        Date now = new Date();
        product.setCreateTime(now);
        product.setUpdateTime(now);
        // 得到文件
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        String imageUrl = uploadFileUtil.uploadFile(file, null);
        product.setImageUrl(imageUrl);
        productService.addProduct(product);
        return resp;
    }

    @DeleteMapping("{id}")
    public ResponseModel delProduct(@PathVariable(value = "id") Integer id, @RequestBody Map<String, String> param) throws Exception {
        ResponseModel resp = new ResponseModel<>();
        productService.delProduct(id);
        if (param == null || param.isEmpty()) {
            throw new DataCheckException(500, "请求参数为空");
        }
        // 删除图片
        String fileName = param.get("fileName");
        String basePath = ResourceUtils.getURL("classpath:").getPath() + "static";
        File file = new File(basePath + fileName);
        if (file.exists()) {//文件是否存在
            file.delete();
        }
        return resp;
    }

    @PutMapping
    public ResponseModel updateProduct(HttpServletRequest request, @RequestBody Product product) throws Exception {
        ResponseModel resp = new ResponseModel<>();
        if (product == null || product.getId() == null) {
            throw new DataCheckException(500, "产品信息为空或者产品ID为空");
        }
        // 判断是否有文件
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        if(!file.isEmpty()){
            // 上传新图片
            String imageUrl = uploadFileUtil.uploadFile(file, null);
            product.setImageUrl(imageUrl);

            // 成功后把原图片删除
            Product productOld = productService.getProductById(product.getId());
            String fileName = productOld.getImageUrl();
            String basePath = ResourceUtils.getURL("classpath:").getPath() + "static";
            File fileOld = new File(basePath + fileName);
            if (fileOld.exists()) {//文件是否存在
                fileOld.delete();
            }

        }
        product.setUpdateTime(new Date());
        productService.updateProduct(product);
        return resp;
    }
}
