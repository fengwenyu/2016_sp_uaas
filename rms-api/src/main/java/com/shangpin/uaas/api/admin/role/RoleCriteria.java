package com.shangpin.uaas.api.admin.role;

/**
 * 角色查询条件
 */
public class RoleCriteria {
    private String nameLike;

    private Boolean isEnabled;

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
