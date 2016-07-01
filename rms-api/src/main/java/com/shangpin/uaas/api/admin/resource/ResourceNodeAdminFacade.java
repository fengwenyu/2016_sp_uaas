package com.shangpin.uaas.api.admin.resource;

import java.util.List;

import com.shangpin.uaas.api.annation.FacadeApi;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;

/**
 * 资源组服务
 */
public interface ResourceNodeAdminFacade {


    /**
     * 根据父机构查找资源<br />
     * 资源是树形结构，当没有输入时候只查询一级的资源
     *
     * @param parentResourceId 父级资源标识 必填
     * @return
     */
    @FacadeApi(params = {"parentResourceId"})
    public List<ResourceNodeDTO> findResourceNodeByParentResourceId(String parentResourceId);

    /**
     * 根据父机构查找资源<br />
     * 资源是树形结构，当没有输入时候只查询一级的资源
     *
     * @param parentResourceId 父级资源标识 必填
     * @return
     */
    public List<ResourceNodeWithFunctionsDTO> findResourceNodeWithFunctionsByParentResourceId(String parentResourceId);

    /**
     * 根据角色主键标识的ID列表获取所有的资源节点
     *
     * @param roleIds   角色主键标识的ID列表
     * @param paginator 分页信息 必填
     * @return 所有的不重复资源节点
     */
    @FacadeApi(params = {"roleIds", "paginator"})
    public PagedList<ResourceNodeDTO> findResourceNodesByRoleId(List<String> roleIds, Paginator paginator);

    /**
     * @param resourceNodeCriteriaDTO
     * @return
     */
    @FacadeApi(params = {"resourceNodeCriteriaDTO"})
    public List<ResourceNodeDTO> findResourceNodesByCriteria(ResourceNodeCriteriaDTO resourceNodeCriteriaDTO);
}
