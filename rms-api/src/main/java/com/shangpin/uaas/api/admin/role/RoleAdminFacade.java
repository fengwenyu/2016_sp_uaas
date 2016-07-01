package com.shangpin.uaas.api.admin.role;

import java.util.List;

import com.shangpin.uaas.api.admin.user.UserCriteriaWithRoleDTO;
import com.shangpin.uaas.api.admin.user.UserWithRoleDTO;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;

/**
 * 角色
 */
public interface RoleAdminFacade {

    /**
     * 创建角色
     *
     * @param role 参考DTO
     * @return 主键
     */
    public String createRole(RoleDTO role);

    /**
     * 更新角色
     *
     * @param role 参考DTO
     */
    public void modifyRole(RoleDTO role);

    /**
     * 获取角色信息
     *
     * @param roleId 角色标识 必填
     * @return 角色信息
     */
    public RoleDTO getRole(String roleId);

    /**
     * 根据角色编号获取角色
     *
     * @param code 角色编号 必填
     * @return 角色信息
     */
    public RoleDTO getRoleByCode(String code);

    /**
     * 删除角色
     *
     * @param roleId 必填
     */
    public void deleteRole(String roleId);

    /**
     * 根据角色名称模糊匹配和状态查询
     *
     * @param roleCriteria 详情参考RoleCriteria
     * @param paginator    分页信息
     * @return
     */
    public PagedList<RoleDTO> findAllRolesByCriteria(RoleCriteria roleCriteria, Paginator paginator);

    /**
     * 给用户添加角色
     *
     * @param userId 用户标识
     * @param roleIds 角色标识 必须 0..n 需要解释0
     */
    public void modifyRoleAndUserBindings(String userId, List<String> roleIds);

    /**
     * 启用、停用角色<br />
     * 关联启用、停用授权权限信息和用户关联角色信息
     *
     * @param roleId    角色标识 必填
     * @param isEnabled 状态 必填
     */
    public void enabledRole(String roleId, Boolean isEnabled);

    /**
     * 获取所有的角色
     *
     * @param paginator 分页信息
     * @return 当前页所有角色信息
     */
    public PagedList<RoleDTO> findAllRoles(Paginator paginator);

    /**
     * 根据用户的标识获取关联角色
     *
     * @param userId    用户唯一标识
     * @param paginator 分页信息
     * @return 当前也的关联角色列表
     */
    public PagedList<RoleDTO> findRolesByUserId(String userId, Paginator paginator);

    /**
     * 查询用户是否拥有当前角色
     *
     * @param criteria  查询条件
     * @param roleId    角色id
     * @param paginator 分页
     * @return 用户信息
     */
    public PagedList<UserWithRoleDTO> findAllUsersWithRoleByCriteria(UserCriteriaWithRoleDTO criteria, String roleId, Paginator paginator);

    /**
     * 给用户授予某种角色
     *
     * @param roleId 角色id
     * @param userId 用户id
     */
    public void assignRoleToUser(String roleId, String userId);

    /**
     * 撤销用户的某种角色
     *
     * @param roleId 角色id
     * @param userId 用户id
     */
    public void revokeRoleFromUser(String roleId, String userId);

}
