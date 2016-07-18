package com.shangpin.uaas.api.admin.org;


import java.util.List;

import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;

/**
 * 机构服务
 */
public interface OrganizationAdminFacade {

    /**
     * 创建部门
     *
     * @param organization 查看DTO -- 必填
     * @return id 主键标识
     */
    public String createOrganization(OrganizationDTO organization);

    /**
     * 更新机构信息
     *
     * @param organization 查看DTO -- 必填
     */
    public void modifyOrganization(OrganizationDTO organization);

    /**
     * 删除机构信息
     *
     * @param id 主键标识--必填
     */
    public void deleteOrganization(String id);

    /**
     * 获取机构信息
     *
     * @param id 主键标识--必填
     */
    public OrganizationDTO getOrganization(String id);

    /**
     * 根据机构信息查找用户
     * <p/>
     * <ul>
     * <li>parentId为空，查询一级机构
     * <li>parentId不为空，根据orgId查询的结果是该机构下的子机构（只查询一级关联）
     * </ul>
     *
     * @param parentId
     * @return
     */
    public PagedList<OrganizationDTO> findAllSubOrganizationsByParentId(String parentId, Paginator paginator);

    /**
     * 查询子部门
     * 该方法只返回下一级的部门
     *
     * @param parentId 上级部门标识
     * @return
     */
    public List<OrganizationDTO> findOneLevelOrganizationsByParentId(String parentId);

    /**
     * 根据部门名称和负责人名称查询部门 <br />
     * 部门名称和负责人要求同时满足条件 <br />
     * 参数为空时候不作为筛选条件
     *
     * @param organizationCriteria
     * @return
     */
    public PagedList<OrganizationDTO> findAllOrganizationsByCriteria(OrganizationCriteriaDTO organizationCriteria, Paginator paginator);

    /**
     * 移动部门
     *
     * @param organizationId       要移动的部门 必填
     * @param parentOrganizationId 移到的目标部门 必填
     */
    public String moveOrganization(String organizationId, String parentOrganizationId);

    /**
     * 获取所有的部门节点
     *
     * @return 所有的部门节点
     */
    public List<OrganizationDTO> getAllOrganizations();

    /**
     * 根据部门编号获取部门
     *
     * @param code
     * @return
     */
    OrganizationDTO findOrganizationByCode(String code);
}
