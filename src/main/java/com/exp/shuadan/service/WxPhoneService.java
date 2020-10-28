package com.exp.shuadan.service;


import com.exp.shuadan.entity.WxPhone;
import com.exp.shuadan.mapper.WxPhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxPhoneService {

    @Autowired
    WxPhoneMapper wxPhoneMapper;

    public WxPhone getNext() {
        return wxPhoneMapper.getNext();
    }
}
