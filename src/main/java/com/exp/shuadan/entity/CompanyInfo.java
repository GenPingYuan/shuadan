package com.exp.shuadan.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CompanyInfo {


    /**
     * companyCode : 708461136
     * companyKey : 48cb7705bcd8c9d7429f792f60ebc998
     * companyName : 深圳市腾讯计算机系统有限公司
     * regNumber : 440301103448669
     * areaCode : 4403
     * areaName : 深圳市
     * companyType : 企业法人
     * issueTime : 1998-11-11
     * regOrgName : 广东省深圳市质量技术监督局
     * bussinessDes : 一般经营项目：计算机软、硬件的设计、技术开发、销售（不含专营、专控、专卖商品及限制项目）；数据库及计算机网络服务；国内商业、物资供销业（不含专营、专控、专卖商品）；第二类增值电信业务中的信息服务业务（不含固定网电话信息服务和互联网信息服务，并按许可证B2-20090028号文办）；信息服务业务（仅限互联网信息服务业务，并按许可证粤B2-20090059号文办）；从事广告业务（法律、行政法规规定应进行广告经营审批等级的，另行办理审批登记后方可经营）；网络游戏出版运营（凭有效的新出网证（粤）字010号互联网出版许可证经营）；货物及技术进出口。许可经营项目：
     * businessStatus : 正常
     * faRen : null
     * regM : 0
     * regType : null
     * address : 深圳市南山区高新区高新南一路飞亚达大厦5-10楼
     */

    private String companyCode;
    private String companyKey;
    private String companyName;
    private String regNumber;
    private String areaCode;
    private String areaName;
    private String companyType;
    private String issueTime;
    private String regOrgName;
    private String bussinessDes;
    private String businessStatus;
    private String faRen;
    private String regM;
    private String regType;
    private String address;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getRegOrgName() {
        return regOrgName;
    }

    public void setRegOrgName(String regOrgName) {
        this.regOrgName = regOrgName;
    }

    public String getBussinessDes() {
        return bussinessDes;
    }

    public void setBussinessDes(String bussinessDes) {
        this.bussinessDes = bussinessDes;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getFaRen() {
        return faRen;
    }

    public void setFaRen(String faRen) {
        this.faRen = faRen;
    }

    public String getRegM() {
        return regM;
    }

    public void setRegM(String regM) {
        this.regM = regM;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "companyCode='" + companyCode + '\'' +
                ", companyKey='" + companyKey + '\'' +
                ", companyName='" + companyName + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", companyType='" + companyType + '\'' +
                ", issueTime='" + issueTime + '\'' +
                ", regOrgName='" + regOrgName + '\'' +
                ", bussinessDes='" + bussinessDes + '\'' +
                ", businessStatus='" + businessStatus + '\'' +
                ", faRen='" + faRen + '\'' +
                ", regM='" + regM + '\'' +
                ", regType='" + regType + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
