package com.shangpin.uaas.api.admin.menu;

import lombok.Getter;
import lombok.Setter;

/**
 * 菜单
 */
@Getter
@Setter
public class MenuDTO {
    /**
     * 主键唯一标识
     */
    private String id;
    /**
     * 显示名称
     */
    private String name;
    /**
     * uri
     */
    private String uri;
    /**
     * 父级标识
     */
    private String parentId;

    private int sort;

    private String appCode;

    private String url;
    /**
     * 是否有父节点
     */
    private Boolean hasParent;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
