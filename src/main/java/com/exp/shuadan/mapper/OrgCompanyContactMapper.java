package com.exp.shuadan.mapper;


import com.exp.shuadan.entity.OrgCompanyContact;
import com.exp.shuadan.entity.WxPhone;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgCompanyContactMapper {

    OrgCompanyContact getCompantContact(String companyName);

    int add(OrgCompanyContact info);
}
