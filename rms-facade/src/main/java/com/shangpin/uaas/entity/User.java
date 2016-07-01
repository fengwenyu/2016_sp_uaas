package com.shangpin.uaas.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shangpin.uaas.api.admin.user.Gender;

import lombok.*;

/**
 * 用户
 * @date:     2016年6月10日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7186953461952043835L;
    private String id;
    private String realName;// 真实姓名
    private String username;// 用户名
    private Gender gender;// 性别
    private String userCode;// 用户编号
    private boolean status;// 用户状态
    private Date createdTime;// 创建时间
    private Date updatedTime;// 更新时间
    private String mobile; // 手机号码
    private String telephone;// 座机
    private String email;// 邮箱
    private String organizationId; // 机构
    private String organizationName; // 机构名称

    private String directLeaderId; //直属领导
    
    private String jobLevel; //职位级别
    
    private String password;
    
    private String workplace;
    
    private Date birth;// 出生日期

    private String jobTitle; //
    
    private String company;
    
    private String entryDate;
    
    private String jobTitleDate;
    private Date contractStartDate;
    private Date probationEndDate;
    private Date positiveDate;
    private Date firstContractEndDate;
    private Date secondContractEndDate;
    private Date turnoverDate;
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
    private Date workStartDate;
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

    //和色关联查询的冗余字段
    private String roleId;
}
