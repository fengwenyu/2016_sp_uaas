package com.shangpin.uaas.api.admin.org;

import java.util.ArrayList;
import java.util.List;

import com.shangpin.uaas.api.admin.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 组织结构DTO
 */
@Getter
@Setter
public class OrganizationDTO {
    /**
     * 创建时不需要，创建成功后返回
     * <p/>
     * <p/>
     * 更新时候必填
     */
    private String id;
    /**
     * 部门编号
     */
    private String code;
    /**
     * 部门名称--必填
     */
    private String name;
    /**
     * 部门描述--可选
     */
    private String description;
    /**
     * 是否启用
     */
    private Boolean isEnabled;
    /**
     * 部门父级标识，部门之间关联时候使用
     * 可选
     */
    private String parentId;

    /**
     * 这个是一个相对路径；例如：产品部/技术部/ .Net...
     * 为了展示使用
     */
    private String rdn;

    /**
     * 是否有子节点
     */
    private Boolean isLeaf;
    /**
     * 是否有父节点
     */
    private Boolean hasParent; //页面渲染树 用
    /**
     * 部门负责人--多个
     * <p/>
     * 在创建机构的时候，只需要UserDTO中的ID即可，其他属性不会存储，也不受影响
     * <p/>
     * 可选
     */
    private List<UserDTO> organizationLeaders = new ArrayList<UserDTO>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<UserDTO> getOrganizationLeaders() {
        return organizationLeaders;
    }

    public void setOrganizationLeaders(List<UserDTO> organizationLeaders) {
        this.organizationLeaders = organizationLeaders;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getRdn() {
        return rdn;
    }

    public void setRdn(String rdn) {
        this.rdn = rdn;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
