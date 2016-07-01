package com.shangpin.uaas.api.facade.user;

import java.io.Serializable;

/**
 * 用户角色关联
 */
public class UserRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
