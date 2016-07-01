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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户组
 */
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Group implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String id;
    private String name; // 角色名称
    private Date createdTime;
    private Date updatedTime;
	
    private String description;// 角色描述
    private boolean status;

}
