package com.shangpin.uaas.api.admin.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 角色DTO
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    /**
     * 角色标识
     */
    private String id;
    /**
     * 角色编码
     */
    private String code;
    /**
     * 状态
     */
    private Boolean isEnabled;
    /**
     * 角色描述
     */
    private String description;

    /**
     * 创建时间－－ 创建角色时候不需要传递
     */
    private String createdTime;

    /**
     * 角色类型
     */
    private String type;

    
}
