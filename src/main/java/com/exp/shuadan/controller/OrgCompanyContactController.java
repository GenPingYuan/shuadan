package com.exp.shuadan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exp.shuadan.entity.CompanyInfo;
import com.exp.shuadan.entity.OrgCompanyContact;
import com.exp.shuadan.entity.ReturnMessage;
import com.exp.shuadan.service.ExcelService;
import com.exp.shuadan.service.OrgCompanyContactService;
import com.seepine.http.util.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/orgContact")
@EnableAsync
public class OrgCompanyContactController {

    private static final Logger log = LoggerFactory.getLogger(OrgCompanyContactController.class);

    @Autowired
    OrgCompanyContactService orgCompanyContactService;

    @Autowired
    ExcelService excelService;

    @RequestMapping("/getByCompName/{companyName}")
    public ReturnMessage getCompantContact(@PathVariable String companyName) throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        orgCompanyContactService.getCompantContact(companyName,session);
         return null;
    }

    @RequestMapping("/add")
    public int add(OrgCompanyContact info) {
        return orgCompanyContactService.add(info);
    }









    /**
     * 上传Excel文件并读取
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String uploadExcel(HttpServletRequest request) throws Exception {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("filename");
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        InputStream inputStream = file.getInputStream();
        List<List<Object>> list = excelService.getBankListByExcel(inputStream, file.getOriginalFilename());
        inputStream.close();

        for (int i = 0; i < list.size(); i++) {
            List<Object> lo = list.get(i);
            //TODO 随意发挥
            String companyName = lo.get(0).toString();
//            Object[] jr = JSONArray.parseArray(lo.toString()).toArray();
//            String companyName = (String)jr[0];
            log.info(companyName);
            //
            companyName = companyName.toLowerCase();
            companyName = companyName.replace(".", "");
            companyName = companyName.replace("ltd", "");
            companyName = companyName.replace("company", "");
            companyName = companyName.replace("co", "");
            companyName = companyName.replace(" ", "");
            companyName = companyName.replace("-", "");
            companyName = companyName.replace("technology", "");
            companyName = companyName.replace("technologies", "");
            log.info(companyName);
            this.getCompantContact(companyName);
        }
        return "上传成功";

    }
}
