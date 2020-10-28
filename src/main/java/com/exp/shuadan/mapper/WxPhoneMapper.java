package com.exp.shuadan.mapper;


import com.exp.shuadan.entity.WxPhone;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface WxPhoneMapper {

    WxPhone getNext();
}
