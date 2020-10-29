package com.exp.shuadan.entity;

public class OrgCompanyContact {

    public Integer id;
    public String companyPy;
    /*
	   信用代码
    **/
    public String creditCode;
    /*

        注册号
    **/
    public String regNumber;
    /*

        企业名称
    **/
    public String companyName;
    /*

        法人
    **/
    public String faRen;
    /*

        区域代码
    **/
    public String areaCode;
    /*

        所属地区
    **/
    public String areaName;
    /*

        电话区号
    **/
    public String phoneArea;
    /*
        联系电话
    **/

    public String phone;
    /*

        邮箱
    **/
    public String email;
    /*

        公司网站
    **/
    public String webSite;
    /*

        公司地址
    **/
    public String address;
    /*

        经营状态
    **/
    public String businessStatus;
    /*

        经营范围
    **/
    public String bussinessDes;

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFaRen() {
        return faRen;
    }

    public void setFaRen(String faRen) {
        this.faRen = faRen;
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

    public String getPhoneArea() {
        return phoneArea;
    }

    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getBussinessDes() {
        return bussinessDes;
    }

    public void setBussinessDes(String bussinessDes) {
        this.bussinessDes = bussinessDes;
    }

    public String getCompanyPy() {
        return companyPy;
    }

    public void setCompanyPy(String companyPy) {
        this.companyPy = companyPy;
    }

    @Override
    public String toString() {
        return "OrgCompanyContact{" +
                "creditCode='" + creditCode + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", faRen='" + faRen + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", phoneArea='" + phoneArea + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", webSite='" + webSite + '\'' +
                ", address='" + address + '\'' +
                ", businessStatus='" + businessStatus + '\'' +
                ", bussinessDes='" + bussinessDes + '\'' +
                '}';
    }
}
