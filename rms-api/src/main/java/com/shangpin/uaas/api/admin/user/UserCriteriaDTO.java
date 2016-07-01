package com.shangpin.uaas.api.admin.user;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Setter
@Getter
public class UserCriteriaDTO {
    //姓名、性别、部门、工作区和状态查询用户
    /**
     * 真实姓名--模糊匹配
     */
    private String realNameLike;
    /**
     * 性别
     */
    private Gender gender;
    /**
     * 部门名称--模糊匹配
     */
    private String organizationNameLike;
//    /**
//     * 手机号码--模糊匹配
//     */
//    private String mobileNameLike;
//    /**
//     * 邮箱--模糊匹配
//     */
//    private String emailNameLike;
    /**
     * 工作区
     */
    private String workLocation;
    /**
     * 用户状态的类型
     */
    private Status status;
    /**
     * 部门标识
     */
    private String organizationId;
/*
    public String getRealNameLike() {
        return realNameLike;
    }

    public void setRealNameLike(String realNameLike) {
        this.realNameLike = realNameLike;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getOrganizationNameLike() {
        return organizationNameLike;
    }

    public void setOrganizationNameLike(String organizationNameLike) {
        this.organizationNameLike = organizationNameLike;
    }

//    public void setEmailNameLike(String emailNameLike){this.emailNameLike = emailNameLike;}
//
//    public void setMobileNameLike(String mobileNameLike){this.mobileNameLike = mobileNameLike;}
//
//    public String getMobileNameLike(){return this.mobileNameLike;}
//
//    public  String getEmailNameLike(){return this.emailNameLike;}

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getorganizationId() {
        return organizationId;
    }

    public void setorganizationId(String organizationId) {
        this.organizationId = organizationId;
    }*/
}
