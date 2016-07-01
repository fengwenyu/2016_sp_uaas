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
 *  角色组
 * @date:     2016年6月10日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3588109737423233938L;
    private String id;
    private String roleId;
    private String groupId;
    private boolean status;
}
