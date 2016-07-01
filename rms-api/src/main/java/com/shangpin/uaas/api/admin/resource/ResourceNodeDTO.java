package com.shangpin.uaas.api.admin.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 资源树
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResourceNodeDTO {

    /**
     * 父级标识
     */
    private String parentId;

    /**
     * 资源标识
     */
    private String resourceId;

    /**
     * 主键
     */
    private String id;
    /**
     * 模块名称
     */
    private String name;
    /**
     * 状态
     */
    private Boolean isEnabled;
    /**
     * 类型:页面，功能
     */
    private ResourceType type;

    /**
     * 是否有子节点
     */
    private Boolean isLeaf;

    /**
     * 是否有父节点
     */
    private Boolean hasParent;
    /**
     * 资源标识：比如说是一个页面地址，功能按钮的ID
     */
    private String uri;

    private String description; // 描述

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

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

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
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
