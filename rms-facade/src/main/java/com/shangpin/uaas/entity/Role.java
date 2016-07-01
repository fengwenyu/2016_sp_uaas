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

import com.shangpin.uaas.api.admin.role.RoleType;

import lombok.*;

/**
 * 角色
 * @date:     2016年6月10日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7707543605754743445L;
    private String id;
    private String name; // 角色名称
    private Date createdTime;
    private Date updatedTime;
    private String description;// 角色描述
    private boolean  status;
    private String type;//1:普通

}
