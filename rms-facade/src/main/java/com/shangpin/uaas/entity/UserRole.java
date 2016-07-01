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
 * 用户关联角色
 * @date:     2016年6月10日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private String roleId;
    private boolean status=true;

}
