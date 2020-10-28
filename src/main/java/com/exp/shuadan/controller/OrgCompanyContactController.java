package com.exp.shuadan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.exp.shuadan.entity.CompanyInfo;
import com.exp.shuadan.entity.OrgCompanyContact;
import com.exp.shuadan.entity.ReturnMessage;
import com.exp.shuadan.service.OrgCompanyContactService;
import com.seepine.http.util.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orgContact")
public class OrgCompanyContactController {

    private String sessionToken;

    private static final String API = "https://api.qianzhan.com";

    private static final String getToken = API + "/OpenPlatformService/GetToken";

    private static final String getCompany = API + "/OpenPlatformService/CombineIndexSearch";

    private static final String getCompanyContact = API + "/OpenPlatformService/OrgCompanyContact";

    private static final Logger log = LoggerFactory.getLogger(OrgCompanyContactController.class);

    @Autowired
    OrgCompanyContactService orgCompanyContactService;

    @RequestMapping("/getByCompName/{companyName}")
    public ReturnMessage getCompantContact(@PathVariable String companyName) throws Exception {
        OrgCompanyContact orgCompanyContact = orgCompanyContactService.getCompantContact(companyName);

        if (orgCompanyContact == null) {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            Object sessionTokenAttri = session.getAttribute("orgToken");
            // 获取sessionToken
            if (sessionTokenAttri == null) {
                getToken(session);
            }

            CompanyInfo[] ciArr =  combineIndexSearch(companyName);
            for (int i = 0; i < ciArr.length; i++) {
                CompanyInfo ci = ciArr[i];
                getCompanyConact(ci.getCompanyName());
            }
            return new ReturnMessage("200", "1", "成功", ciArr);
        }else {
            ReturnMessage rm = new ReturnMessage("200", "1", "成功", orgCompanyContact);
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

    public CompanyInfo[] combineIndexSearch(String companyName) throws Exception {
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

    public void getCompanyConact(String companyName) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", this.sessionToken);
        map.put("type", "JSON");
        map.put("companyName", companyName);
        map.put("page", "1");
        map.put("pagesize", "10000");
        String getCtResp = HttpUtil.get(getCompanyContact,map);
            //使用fastjson将字符串转换为需要的对象
            JSONObject ctjo = JSON.parseObject(getCtResp);
            log.info(getCtResp);
            ReturnMessage ctrm = ctjo.toJavaObject(ReturnMessage.class);
            log.info(ctrm.toString());
            OrgCompanyContact[] ocArr = ctrm.resultToArr(OrgCompanyContact.class);
            for (int i = 0; i < ocArr.length; i++) {
                this.add(ocArr[i]);
            }
    }
}
