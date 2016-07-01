package com.shangpin.uaas.api.facade.auth.dto;

import java.io.Serializable;

public class OrganizationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时不需要，创建成功后返回
     * <p/>
     * <p/>
     * 更新时候必填
     */
    private String id;
    /**
     * 部门名称--必填
     */
    private String name;
    /**
     * 部门编号
     */
    private String code;
    /**
     * 部门父级标识，部门之间关联时候使用
     * 可选
     */
    private String parentId;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
