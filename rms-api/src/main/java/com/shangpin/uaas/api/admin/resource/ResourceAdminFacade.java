package com.shangpin.uaas.api.admin.resource;

import java.util.List;

import com.shangpin.uaas.api.annation.FacadeApi;

/**
 * 资源管理<br />
 * 该模块可以对资源（需求中说的权限）的管理（增删改查）
 */
public interface ResourceAdminFacade {
    /**
     * 添加资源
     *
     * @param resourceNodeDTO 参考DTO
     * @return 主键--必填
     */
    @FacadeApi(params = {"resourceNodeDTO"})
    public String createResource(ResourceNodeDTO resourceNodeDTO);

    /**
     * 返回资源信息
     *
     * @param resourceNodeId 资源Node标识 必填
     */
    @FacadeApi(params = {"resourceNodeId"})
    public ResourceNodeDTO getResourceNode(String resourceNodeId);

    /**
     * 修改资源
     *
     * @param resourceDTO 参考DTO
     */
    @FacadeApi(params = {"resourceDTO"})
    public String modifyResource(ResourceNodeDTO resourceDTO);

    /**
     * 删除资源
     * <p/>
     * 如果该资源节点下的资源只关联该资源节点，则删除该资源节点会把相应的资源删除
     *
     * @param resourceNodeId 资源组标识--必填
     */
    @FacadeApi(params = {"resourceNodeId"})
    public void deleteResource(String resourceNodeId);

    /**
     * 启用、停用资源<br />
     * 关联启用、停用授权角色信息
     *
     * @param resourceNodeId 资源标识 必填
     * @param isEnabled  是否启用 必填
     */
    @FacadeApi(params = {"resourceNodeId" , "isEnabled"})
    public void modifyEnable(String resourceNodeId, Boolean isEnabled);

    /**
     * 获取所有资源
     */
    @FacadeApi()
    public List<ResourceNodeDTO> getAllResources();

    @FacadeApi(params = {"parentResourceName"})
    public List<ResourceNodeDTO> getSubResourcesByParentResourcesName(String parentResourceName);

}
