package com.shangpin.uaas.api.admin.resource;

/**
 * 资源DTO
 */
public class ResourceDTO {
    private String id; // 主键
    private String name; // 模块名称
    private Boolean isEnabled; // 状态
    private String type; // 类型:页面，功能
    private String uri; // 资源标识：比如说是一个页面地址，功能按钮的ID
    private String description; // 描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
