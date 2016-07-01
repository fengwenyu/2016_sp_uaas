package com.shangpin.uaas.api.admin.permission;

/**
 * 权限
 */
public class PermissionDTO {

    /**
     * 主键
     */
    private String id;
    /**
     * 角色标识
     */
    private String roleId;
    /**
     * 资源标识
     */
    private String resourceId;
    /**
     * 是否可用
     */
    private Boolean isEnabled;
    /**
     * 描述信息
     */
    private String description;
    /**
     * URI
     */
    private String uri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
