package com.shangpin.uaas.api.admin.user;

public class UserWithRoleDTO {
    private Boolean hasThisRole;

    public Boolean getHasThisRole() {
        return hasThisRole;
    }

    public void setHasThisRole(Boolean hasThisRole) {
        this.hasThisRole = hasThisRole;
    }

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

    private String jobTitle; //
    private String company;
    private String entryDate;
    private String jobTitleDate;
    private String contractStartDate;
    private String probationEndDate;
    private String positiveDate;
    private String firstContractEndDate;
    private String secondContractEndDate;
    private String turnoverDate;
    private String employeeStatus;

    private String identity;
    private String identityNumber;
    private String age;
    private String nation;
    private String nationality;
    private String education;
    private String degree;
    private String learningType;
    private String politicalAffiliation;
    private String workStartDate;
    private String graduationTime;
    private String familyRegister;
    private String foreignEnglish;
    private String foreignRussian;
    private String foreignFrench;
    private String foreignKorean;
    private String foreignJapanese;

    private String maritalStatus;
    private String birthPlace;
    private String salaryBank;
    private String salaryBankNumber;


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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getJobTitleDate() {
        return jobTitleDate;
    }

    public void setJobTitleDate(String jobTitleDate) {
        this.jobTitleDate = jobTitleDate;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getProbationEndDate() {
        return probationEndDate;
    }

    public void setProbationEndDate(String probationEndDate) {
        this.probationEndDate = probationEndDate;
    }

    public String getPositiveDate() {
        return positiveDate;
    }

    public void setPositiveDate(String positiveDate) {
        this.positiveDate = positiveDate;
    }

    public String getFirstContractEndDate() {
        return firstContractEndDate;
    }

    public void setFirstContractEndDate(String firstContractEndDate) {
        this.firstContractEndDate = firstContractEndDate;
    }

    public String getSecondContractEndDate() {
        return secondContractEndDate;
    }

    public void setSecondContractEndDate(String secondContractEndDate) {
        this.secondContractEndDate = secondContractEndDate;
    }

    public String getTurnoverDate() {
        return turnoverDate;
    }

    public void setTurnoverDate(String turnoverDate) {
        this.turnoverDate = turnoverDate;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getLearningType() {
        return learningType;
    }

    public void setLearningType(String learningType) {
        this.learningType = learningType;
    }

    public String getPoliticalAffiliation() {
        return politicalAffiliation;
    }

    public void setPoliticalAffiliation(String politicalAffiliation) {
        this.politicalAffiliation = politicalAffiliation;
    }

    public String getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(String workStartDate) {
        this.workStartDate = workStartDate;
    }

    public String getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(String graduationTime) {
        this.graduationTime = graduationTime;
    }

    public String getFamilyRegister() {
        return familyRegister;
    }

    public void setFamilyRegister(String familyRegister) {
        this.familyRegister = familyRegister;
    }

    public String getForeignEnglish() {
        return foreignEnglish;
    }

    public void setForeignEnglish(String foreignEnglish) {
        this.foreignEnglish = foreignEnglish;
    }

    public String getForeignRussian() {
        return foreignRussian;
    }

    public void setForeignRussian(String foreignRussian) {
        this.foreignRussian = foreignRussian;
    }

    public String getForeignFrench() {
        return foreignFrench;
    }

    public void setForeignFrench(String foreignFrench) {
        this.foreignFrench = foreignFrench;
    }

    public String getForeignKorean() {
        return foreignKorean;
    }

    public void setForeignKorean(String foreignKorean) {
        this.foreignKorean = foreignKorean;
    }

    public String getForeignJapanese() {
        return foreignJapanese;
    }

    public void setForeignJapanese(String foreignJapanese) {
        this.foreignJapanese = foreignJapanese;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getSalaryBank() {
        return salaryBank;
    }

    public void setSalaryBank(String salaryBank) {
        this.salaryBank = salaryBank;
    }

    public String getSalaryBankNumber() {
        return salaryBankNumber;
    }

    public void setSalaryBankNumber(String salaryBankNumber) {
        this.salaryBankNumber = salaryBankNumber;
    }
}
