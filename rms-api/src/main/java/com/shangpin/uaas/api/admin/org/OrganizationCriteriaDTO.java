package com.shangpin.uaas.api.admin.org;

/**
 */
public class OrganizationCriteriaDTO {

    /**
     * 部门名称模糊匹配
     */
    private String organizationNameLike;
    /**
     * 部门负责人模糊匹配
     */
    private String leaderNameLike;

    public String getOrganizationNameLike() {
        return organizationNameLike;
    }

    public void setOrganizationNameLike(String organizationNameLike) {
        this.organizationNameLike = organizationNameLike;
    }

    public String getLeaderNameLike() {
        return leaderNameLike;
    }

    public void setLeaderNameLike(String leaderNameLike) {
        this.leaderNameLike = leaderNameLike;
    }
}
