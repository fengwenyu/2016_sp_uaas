package com.shangpin.uaas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 角色关联资源
 * @date:     2016年6月10日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4480794765304080005L;

    private String id;
    private String roleId;
    private String resourceId;
    private boolean isEnabled=true;

}
