package com.shangpin.uaas.api.admin.user;

/**
 * Created by Administrator on 2014/8/19.
 */
public class UserWithGroupDTO {
    Boolean hasThisGroup;
    /**
     * 系统产生的主键，无业务意义
     * <p/>
     * 创建用户时候，不需要输入
     * <p/>
     * 更新用户时候必须输入
     */
    private String id;
    /**
     * 真实姓名--必须
     */
    private String realName;
    /**
     * 用户名--必须
     */
    private String username;
    /**
     * 性别--枚举--必须
     */
    private Gender gender;
    /**
     * 用户编号--必须
     */
    private String userCode;
    /**
     * 用户状态--枚举--必须
     */
    private Status status;
    /**
     * 手机号码--建议--登录时候可以使用手机号码登录
     */
    private String mobile;
    /**
     * 座机--可选
     */
    private String telephone;
    /**
     * 邮箱--建议--登录时候可以使用手机号码登录
     */
    private String email;
    /**
     * 隶属机构部门--必须
     */
    private String organizationId;

    /**
     * 隶属机构部门名称--显示时候使用
     */
    private String organizationName;
    /**
     * 直属领导--可选
     */
    private String directLeaderId;
    /**
     * 职位级别：1:;2:;3:;4:;5:;...
     */
    private String jobLevel;

    /**
     * 创建时候必须，获取用户为空
     */
    private String password;

    /**
     * 办公区
     */
    private String workPlace;

    /**
     * 出生日期--格式：yyyy-MM-dd
     */
    private String birth;

    /**
     * 创建时间－－展示使用，创建用户时候不需要传递
     */
    private String createdTime;

    public Boolean getHasThisGroup() {
        return hasThisGroup;
    }

    public void setHasThisGroup(Boolean hasThisGroup) {
        this.hasThisGroup = hasThisGroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDirectLeaderId() {
        return directLeaderId;
    }

    public void setDirectLeaderId(String directLeaderId) {
        this.directLeaderId = directLeaderId;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
