package com.exp.shuadan.service;


import com.exp.shuadan.entity.OrgCompanyContact;
import com.exp.shuadan.mapper.OrgCompanyContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgCompanyContactService {

    @Autowired
    OrgCompanyContactMapper orgCompanyContactMapper;

    public OrgCompanyContact getCompantContact(String companyName) {
        return orgCompanyContactMapper.getCompantContact(companyName);
    }

    public  int add(OrgCompanyContact info) {
        return orgCompanyContactMapper.add(info);
    }
}
