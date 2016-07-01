package com.shangpin.uaas.api.admin.menu;

import java.util.List;

import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;

/**
 * 菜单服务接口
 */
public interface MenuAdminFacade {

    /**
     * 创建菜单
     *
     * @param menuDTO 详细查看DTO
     * @return 主键
     */
    public String createMenu(MenuDTO menuDTO);

    /**
     * 修改菜单
     *
     * @param menuDTO 详细查看DTO
     */
    public void modifyMenu(MenuDTO menuDTO);

    /**
     * 移动菜单
     *
     * @param menuId       当前菜单的唯一标识
     * @param targetMenuId 目标菜单的唯一标识
     */
    public void moveMenu(String menuId, String targetMenuId);

    /**
     * 删除菜单
     *
     * @param menuId 菜单的唯一标识
     */
    public void deleteMenu(String menuId);

    /**
     * 根据父级ID获取子部门
     *
     * @param parentId  父级标识
     * @param paginator 分页参数
     * @return 分页信息菜单项
     */
    public PagedList<MenuDTO> findMenusByParentId(String parentId, Paginator paginator);

    /**
     * 获取菜单项
     *
     * @param id
     * @return 菜单项
     */
    public MenuDTO getMenu(String id);

    /**
     * 获取所有的菜单节点
     *
     * @return 所有的菜单节点
     */
    public List<MenuDTO> getAllMenus();

}
