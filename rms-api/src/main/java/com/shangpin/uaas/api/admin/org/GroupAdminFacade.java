package com.shangpin.uaas.api.admin.org;

import java.util.List;

import com.shangpin.uaas.api.admin.role.RoleDTO;
import com.shangpin.uaas.api.admin.user.UserCriteriaDTO;
import com.shangpin.uaas.api.admin.user.UserWithGroupDTO;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;

/**
 * Created by Administrator on 2014/8/18.
 */
public interface GroupAdminFacade {

    /**
     * 创建用户组
     *
     * @param groupDTO 必填
     * @return 主键
     */
    public String createGroup(GroupDTO groupDTO);

    /**
     * 更新用户组
     *
     * @param groupDTO 必填
     */
    public void updateGroup(GroupDTO groupDTO);

    /**
     * 获取用户组
     *
     * @param id 用户组主键 必填
     * @return 该用户组
     */
    public GroupDTO getGroup(String id);

    /**
     * 获取所有用户组
     *
     * @param paginator 分页信息
     * @return 相应的用户组
     */
    public PagedList<GroupDTO> findAllGroups(Paginator paginator);

    /**
     * 根据用户获取其所有的用户组
     *
     * @param userId 用户ID 必填
     * @return 返回该用户所有的用户组
     */
    public List<GroupDTO> findGroupByUserId(String userId);

    /**
     * 根据工作组名称查询工作组
     *
     * @param name 工作组名称 必填
     * @return 符合的工作组列表
     */
    public List<GroupDTO> findGroupsByName(String name);

    /**
     * 获取该用户组下的所有用户
     *
     * @param groupId 用户组主键 必填
     * @return 该用户组下所有的用户
     */
    public PagedList<UserWithGroupDTO> findUserByGroupId(UserCriteriaDTO userCriteriaDTO, String groupId, Paginator paginator);

    /**
     * 启用/停用用户组
     *
     * @param groupId 用户组主键 必填
     * @param isEnabled 状态 必填
     */
    public void enabledGroup(String groupId, Boolean isEnabled);

    /**
     * 根据用户组获取相应的角色
     *
     * @param groupId 用户组主键 必填
     * @return 相应的角色
     */
    public List<RoleDTO> findRolesByGroupId(String groupId);

    /**
     * 为用户绑定相应的用户组
     *
     * @param userId 用户ID 必填
     * @param groupIds 用户组标示列表 0..N
     */
    public void bindingUserWithGroups(String  userId, List<String> groupIds);

    /**
     * 添加用户到用户组
     *
     * @param userId 用户ID 必填
     * @param groupId 用户组主键 必填
     */
    public void addUserToGroup(String userId, String groupId);

    /**
     * 从用户组移除该用户
     *
     * @param userId 用户ID 必填
     * @param groupId 用户组主键 必填
     */
    public void revokeUserToGroup(String userId, String groupId);

    /**
     * 为用户组绑定角色列表
     *
     * @param groupId 用户组主键 必填
     * @param roleIds 角色主键列表 0..N
     */
    public void bindingRolesWithGroup(String groupId, List<String> roleIds);
}
