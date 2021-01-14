package com.exp.shuadan.util;

import com.exp.shuadan.config.DataCheckException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public class UploadFileUtil {
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 上传文件
     *
     * @param multipartFile 文件对象
     * @param dir           上传目录
     * @return
     */
    public String uploadFile(MultipartFile multipartFile, String dir) throws Exception {
        if (multipartFile.isEmpty()) {
            throw new DataCheckException(500, "请选择文件");
        }
        // 获取文件的名称
        String originalFilename = multipartFile.getOriginalFilename();
        // 文件后缀 例如：.png
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // uuid 生成文件名
        String uuid = String.valueOf(UUID.randomUUID()).replace("-", "");
        // 根路径，在 resources/static/upload
        String path = uploadPath + (StringUtils.isNotBlank(dir) ? (dir + "/") : "");
        // 新的文件名，使用uuid生成文件名
        String fileName = uuid + fileSuffix;
        // 创建新的文件
        File fileExist = new File(path);
        // 文件夹不存在，则新建
        if (!fileExist.exists()) {
            fileExist.mkdirs();
        }
        // 获取文件对象
        File file = new File(path, fileName);
        // 完成文件的上传
        multipartFile.transferTo(file);
        // 返回相对路径
        return path + fileName;
    }
}
