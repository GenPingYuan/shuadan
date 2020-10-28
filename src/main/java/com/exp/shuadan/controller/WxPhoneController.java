package com.exp.shuadan.controller;

import com.exp.shuadan.entity.WxPhone;
import com.exp.shuadan.service.WxPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wxphone")
public class WxPhoneController {

    @Autowired
    WxPhoneService wxPhoneService;

    @RequestMapping("/getNext")
    public String getNext() {
        WxPhone wxPhone = wxPhoneService.getNext();
        if(wxPhone == null) {
            return "0";
        }
        return wxPhone.toString();
    }
}
