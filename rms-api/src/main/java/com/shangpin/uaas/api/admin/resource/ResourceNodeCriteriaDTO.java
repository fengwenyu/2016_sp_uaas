package com.shangpin.uaas.api.admin.resource;

/**
 * Created by Percy on 14-7-19.
 */
public class ResourceNodeCriteriaDTO {
    /**
     * 模块名称
     */
    private String nameLike;

    /**
     * 状态
     */
    private String isEnabled;

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }
}
