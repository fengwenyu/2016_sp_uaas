package com.shangpin.uaas.api.admin.user;

public class UserCriteriaWithRoleDTO {

    Boolean hasThisRole;

    public Boolean getHasThisRole() {
        return hasThisRole;
    }

    public void setHasThisRole(Boolean hasThisRole) {
        this.hasThisRole = hasThisRole;
    }

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
    }
}
