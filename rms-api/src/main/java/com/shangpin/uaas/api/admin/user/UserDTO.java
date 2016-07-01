package com.shangpin.uaas.api.admin.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO{
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

    //关联role查询冗余字段
    private String roleId;
}
