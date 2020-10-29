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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orgContact")
@EnableAsync
public class OrgCompanyContactController {

    private String sessionToken;

    private static final String API = "https://api.qianzhan.com";

    private static final String getToken = API + "/OpenPlatformService/GetToken";

    private static final String getCompany = API + "/OpenPlatformService/CombineIndexSearch";

    private static final String getCompanyContact = API + "/OpenPlatformService/OrgCompanyContact";

    private static final Logger log = LoggerFactory.getLogger(OrgCompanyContactController.class);

    @Autowired
    OrgCompanyContactService orgCompanyContactService;

    @Autowired
    ExcelService excelService;

    @RequestMapping("/getByCompName/{companyName}")
    public ReturnMessage getCompantContact(@PathVariable String companyName) throws Exception {
        int orgCompanyContact = orgCompanyContactService.getCompanyByCompanyPy(companyName);

        if (orgCompanyContact == 0) {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            Object sessionTokenAttri = session.getAttribute("orgToken");
            // 获取sessionToken
            if (sessionTokenAttri == null) {
                getToken(session);
            }

            CompanyInfo[] ciArr = combineIndexSearch(companyName);
            for (int i = 0; i < ciArr.length; i++) {
                CompanyInfo ci = ciArr[i];
                if(ci != null) {
                    getCompanyConact(ci.getCompanyName(),companyName);
                }
            }
            return new ReturnMessage("200", "1", "成功", ciArr);
        } else {
            ReturnMessage rm = new ReturnMessage("200", "1", "成功", null);
            return rm;
        }
    }


    @RequestMapping("/add")
    public int add(OrgCompanyContact info) {
        return orgCompanyContactService.add(info);
    }


    public void getToken(HttpSession session) throws Exception {
        HashMap<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("appkey", "49b9a23feba084f5");
        tokenMap.put("seckey", "af34e25a4692ce57");
        String getTokenResp = HttpUtil.get(getToken, tokenMap);
        log.info(getTokenResp);
//        if (getTokenResp.getStatusLine().getStatusCode() == 200) {
//            String s = EntityUtils.toString(getTokenResp.getEntity());
        JSONObject jo = JSON.parseObject(getTokenResp);
        Map rsm = jo.toJavaObject(Map.class);
        Map resultMap = (Map) rsm.get("result");
        String token = (String) resultMap.get("token");
        log.info(token);
        session.setAttribute("orgToken", token);
        this.sessionToken = token;
//        }
    }

    private CompanyInfo[] combineIndexSearch(String companyName) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", sessionToken);
        map.put("type", "JSON");
        map.put("companyName", companyName);
        map.put("areaCode", "");
        map.put("faRen", "");
        map.put("bussinessDes", "");
        map.put("gd", "");
        map.put("page", "1");
        map.put("pagesize", "10");
        String resp = HttpUtil.post(getCompany, map);
        JSONObject ctjo = JSON.parseObject(resp);
        ReturnMessage rms = ctjo.toJavaObject(ReturnMessage.class);
        log.info(resp);
        CompanyInfo[] result = rms.resultToArr(CompanyInfo.class);
        return result;
    }

    private void getCompanyConact(String companyName,String companyPy) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", this.sessionToken);
        map.put("type", "JSON");
        map.put("companyName", companyName);
        map.put("page", "1");
        map.put("pagesize", "10000");
        String getCtResp = HttpUtil.get(getCompanyContact, map);
        //使用fastjson将字符串转换为需要的对象
        JSONObject ctjo = JSON.parseObject(getCtResp);
        log.info(getCtResp);
        ReturnMessage ctrm = ctjo.toJavaObject(ReturnMessage.class);
        log.info(ctrm.toString());
        OrgCompanyContact[] ocArr = ctrm.resultToArr(OrgCompanyContact.class);
        for (int i = 0; i < ocArr.length; i++) {
            OrgCompanyContact occ = ocArr[i];
            occ.setCompanyPy(companyPy);
            this.add(occ);
        }
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
