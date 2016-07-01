package com.shangpin.uaas.api.facade.auth.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @date:     2016年6月10日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
@Setter
@Getter
@NoArgsConstructor
public class GroupDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7843551010401354555L;
	/**
     * 主键标识
     */
    private String id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    private String status;

}
