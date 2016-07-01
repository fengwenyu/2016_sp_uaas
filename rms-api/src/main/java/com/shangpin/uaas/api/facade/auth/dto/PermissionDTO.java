package com.shangpin.uaas.api.facade.auth.dto;

import java.io.Serializable;

/**
 * 权限
 */
public class PermissionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色标识
     */
    private String roleId;
    /**
     * 资源标识
     */
    private String resourceId;
    /**
     * URI
     */
    private String uri;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
