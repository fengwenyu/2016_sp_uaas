package com.shangpin.uaas.api.admin.user;
import com.shangpin.uaas.api.annation.FacadeApi;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;
/**
 * 用户管理
 */

public interface UserAdminFacade {

    /**
     * 创建用户
     *
     * @param user 参考UserDTO 必填
     * @return 唯一标识
     */
    @FacadeApi(params = {"user"})
    public String createUser(UserDTO user);

    /**
     * 更新用户信息
     *
     * @param user 参考UserDTO 必填
     */
    @FacadeApi(params = {"user"})
    public void modifyUser(UserDTO user);

    /**
     * 获取用户信息
     *
     * @param userId 用户标识 必填
     * @return
     */
    @FacadeApi(params = {"userId"})
    public UserDTO getUser(String userId);

    /**
     * 根据姓名、性别、部门、工作区和状态查询用户
     *
     * @param userCriteriaDTO 参考UserCriteriaDTO
     * @param paginator
     * @return 用户信息列表
     */
    public PagedList<UserDTO> findAllUsersByCriteria(UserCriteriaDTO userCriteriaDTO, Paginator paginator);

    /**
     * 启用、停用用户<br />
     * 关联启用、停用授权角色信息
     *
     * @param userId 必填
     * @param status 必填
     */
    @FacadeApi(params = {"userId", "status"})
    public void modifyEnable(String userId, Status status);

    /**
     * 删除用户
     * <p/>
     * 注意：删除用户后不能恢复，该用户所关联的角色一并删除
     *
     * @param userId 用户标识 必填
     */
    @FacadeApi(params = {"userId"})
    public void deleteUser(String userId);

    /**
     * 根据用户编号查询用户
     *
     * @param userCode 用户编号，必填
     * @return 该编号的用户
     */
    @FacadeApi(params = {"userCode"})
    public UserDTO getUserByUserCode(String userCode);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名，必填
     * @return 该用户名的用户
     */
    @FacadeApi(params = {"username"})
    public UserDTO getUserByUsername(String username);

    /**
     * 根据部门ID获取该部门下所有的用户
     * <p/>
     * 多层级关系的用户
     *
     * @param organizationId 部门标识
     * @param paginator      分页信息
     * @return 相关的所有用户
     */
    @FacadeApi(params = {"organizationId", "paginator"})
    public PagedList<UserDTO> findAllTreeUsersByOrganizationId(String organizationId, Paginator paginator);

    /**
     * 校验用户名是否有重复
     * @param userId 屏蔽查找的用户ID
     * @param userName 查找用户名关键字
     * @return 不存在该用户名返回true ，否则返回false
     */
    @FacadeApi(params = {"userId", "userName"})
    public boolean verifyUserName(String userId , String userName);
    /**
     * 获取用户年龄
     * @param date 生日 yyyy/MM/dd
     */
    @FacadeApi(params = {"userId", "userName"})
    public String getUserAge(String date);

}
