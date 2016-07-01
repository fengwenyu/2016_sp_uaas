package com.shangpin.uaas.api.facade.user;

import java.util.List;
import java.util.Map;

import com.shangpin.uaas.api.annation.FacadeApi;
import com.shangpin.uaas.api.facade.auth.dto.GroupDTO;
import com.shangpin.uaas.api.facade.auth.dto.MenuDTO;
import com.shangpin.uaas.api.facade.auth.dto.OrganizationDTO;
import com.shangpin.uaas.api.facade.auth.dto.RoleDTO;

/**
 * 用户服务
 */
public interface UserFacade {

    /**
     * 获取当前用户信息
     *
     * @param token 必填
     * @return 用户信息 详情参考UserDTO
     */
    @FacadeApi(params = {"token"})
    public UserDTO getUser(String token);

    /**
     * 获取当前用户所在的部门
     *
     * @param token 必填
     * @return 当前用户所在部门信息
     */
    @FacadeApi(params = {"token"})
    public OrganizationDTO findOrganizationByToken(String token);

    /**
     * 获取当前用户的所有角色
     *
     * @param token 访问令牌 必填
     * @return 当前用户拥有的角色列表
     */
    @FacadeApi(params = {"token"})
    public List<RoleDTO> findAllRolesByToken(String token);

    /**
     * 获取当前用户拥有的该系统菜单列表
     * <p/>
     * 该接口只返回父级菜单下一级菜单项（这里要求一级页面必须授权，就是菜单的最顶级必须授权）
     *
     * @param token    访问令牌 必填
     * @param appCode  指的是添加权限时候的一级权限的名字
     * @return 当前用户拥有的角色列表
     */
    @FacadeApi(params = {"token", "appCode"})
    List<MenuDTO> findMenusByAppCode(String token, String appCode);

    /**
     * 获取当前用户拥有的顶级项目的列表
     *
     * 登录完后，返回到默认页时候，用户可以进入的系统
     *
     * @param token 访问令牌 必填
     * @return 返回该用户拥有项目的权限列表（只是最顶级Menu->ParentId === "1"）
     */
    @FacadeApi(params = {"token"})
    List<MenuDTO> findTopMenusByToken(String token);

    /**
     * 根据用户标识获取其他用户信息
     *
     * @param token  必填
     * @param userId 用户ID
     * @return 用户信息 详情参考UserDTO
     */
    @FacadeApi(params = {"token", "userId"})
    public UserDTO getUserByTokenAndUserId(String token, String userId);

    /**
     * 根据父级部门获取下级子部门
     *
     * @param token    必填
     * @param parentId 父级部门ID
     * @return 父级部门获取下级子部门
     */
    @FacadeApi(params = {"token", "parentId"})
    public List<OrganizationDTO> findOrganizationsByTokenAndParentId(String token, String parentId);

    /**
     * 获取所有部门
     *
     * @param token 必填
     * @return 所有部门
     */
    @FacadeApi(params = {"token"})
    public List<OrganizationDTO> findAllOrganizationsByToken(String token);

    /**
     * 根据部门ID获取该部门下所有用户
     *
     * @param token          必填
     * @param organizationId 部门编号
     * @return
     */
    @FacadeApi(params = {"token", "organizationId"})
    public List<UserDTO> findUsersByOrganizationId(String token, String organizationId);

    /**
     * 根据角色列表获取相应角色下的用户（并集）
     *
     * @param token 访问令牌 必填
     * @param roleIds 角色标示列表 至少一个
     * @return 相应角色下的用户列表
     */
    @FacadeApi(params = {"token", "roles"})
    public List<UserDTO> findUsersByRoles(String token, List<String> roleIds);

    /**
     * 根据用户组获取该组内所有的用户列表
     *
     * @param token 访问令牌 必填
     * @param groupId 用户组标识 必填
     * @return 用户列表
     */
    @FacadeApi(params = {"token", "groupId"})
    public List<UserDTO> findUsersByGroup(String token, String groupId);

    /**
     * 根据用户获取所在相应的用户组
     *
     * @param token 访问令牌 必填
     * @param userId 用户标识 必填
     * @return 用户所在用户组列表
     */
    @FacadeApi(params = {"token", "userId"})
    public List<GroupDTO> findGroupsByUser(String token, String userId);

    /**
     * 获取所有的用户组
     *
     * @param token 访问令牌 必填
     * @return 所有的用户组
     */
    @FacadeApi(params = {"token"})
    public List<GroupDTO> findAllGroups(String token);

    /**
     *
     * 修改用户密码
     * @param token
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @FacadeApi(params = {"token" , "oldPassword" , "newPassword"})
    public Map<String,String> changePassword(String token , String oldPassword , String newPassword);

}
