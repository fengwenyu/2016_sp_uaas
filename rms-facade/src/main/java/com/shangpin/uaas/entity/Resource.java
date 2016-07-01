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
 * 资源<br />
 * 模块、页面、功能
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Resource implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3754338002012182968L;
    public static final String PARENT = "1";
    private String id; // 主键
    private boolean enabled=true; // 状态
    private String uri; // 页面地址
    private String functionId; // 功能ID


}
