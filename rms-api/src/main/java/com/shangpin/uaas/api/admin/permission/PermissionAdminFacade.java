package com.shangpin.uaas.api.admin.permission;

import java.util.List;

import com.shangpin.uaas.api.admin.resource.ResourceNodeDTO;
import com.shangpin.uaas.api.admin.role.RoleDTO;
import com.shangpin.uaas.api.annation.FacadeApi;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;

/**
 * 权限管理
 */
public interface PermissionAdminFacade {

    /**
     * 根据角色ID列表获取所有资源列表
     *
     * @param roleIds   1..n
     * @param paginator
     * @return
     */
    public PagedList<PermissionDTO> findAllPermissionsByRoles(List<String> roleIds, Paginator paginator);

    /**
     * 根据权限查询关联的角色
     *
     * @param resourceNodeId 资源Node标识 必填
     * @return
     */
    @FacadeApi(params = {"resourceNodeId"})
    public List<RoleDTO> findAllRolesByResourceNode(String resourceNodeId);

    /**
     * 根据权限查询关联的角色
     * <p/>
     * 该方法会首先删除该角色相关的资源，然后重新绑定
     *
     * @param roleId          角色标识 必填
     * @param resourceNodeIds 资源Node标识
     */
    @FacadeApi(params = {"roleId", "permissionIds"})
    void bindRoleWithPermissions(String roleId, List<String> resourceNodeIds);

    /**
     * 绑定权限到资源
     * <p/>
     * 注意，
     *
     * @param resourceNodeId
     * @param roleIds
     */
    @FacadeApi(params = {"resourceNodeId", "roleIds"})
    void bindPermissionWithRoles(String resourceNodeId, List<String> roleIds);

    /**
     * 根据角色列表获取资源节点列表（该角色列表拥有权限）
     *
     * @param roleIds
     * @param resourceType "BUTTON" / "PAGE"
     * @param paginator
     * @return
     */
    @FacadeApi(params = {"roleIds", "resourceType", "paginator"})
    PagedList<ResourceNodeDTO> findAllResourceNodesByRoles(List<String> roleIds, String resourceType, Paginator paginator);

}

