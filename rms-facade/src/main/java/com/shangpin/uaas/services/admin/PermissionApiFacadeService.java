package com.shangpin.uaas.services.admin;

import com.shangpin.uaas.api.facade.auth.dto.MenuDTO;
import com.shangpin.uaas.convert.api.MenuConverter;
import com.shangpin.uaas.entity.*;
import com.shangpin.uaas.services.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

//@Repository
@Service
public class PermissionApiFacadeService {
	protected static Logger log = LoggerFactory.getLogger(PermissionApiFacadeService.class);
	@Autowired
	private PermissionRepoService permissionRepoService;

	@Autowired
    private MenuRepoService menuRepoService;

    @Autowired
    private UserGroupRepoService userGroupRepoService;
    @Autowired
    private UserRoleRepoService userRoleRepoService;

    @Autowired
    private RoleGroupRepoService roleGroupRepoService;
    @Autowired
    ResourceRepoService resourceRepoService;

//    @Autowired
//    private GroupRepoService groupRepoService;

//    void serviceMethod() {
//
//    }
    public List<Permission> getAllPermissionByUserId(String userId) {
        return permissionRepoService.findByUserId(userId);
    }

    /**
     * 根据用户id和appCode获取用户的菜单
     * @param userId 用户id
     * @param appCode 哪个系统
     */
    public List<MenuDTO> findMenusByUserIdAndAppCode(String userId, String appCode) {

        Set<MenuDTO> menuDTOs = new HashSet<>();
        List<Menu> menus = menuRepoService.findByAppCode(appCode);
        List<Menu> topMenu = menuRepoService.findByParentId("1");
        menus.addAll(topMenu);
        for (Menu menu : menus) {
            menuDTOs.add(MenuConverter.convert(menu));
        }
        List<MenuDTO> results = new ArrayList<>();
        log.debug("最终需要认证的菜单数为："+menuDTOs.size());
        List<Permission> permissionDTOs = getAllPermissionByUserId(userId);
        List<String> resourceIds = new ArrayList<>();
        for (Permission permission : permissionDTOs) {
            resourceIds.add(permission.getResourceId());
        }
        List<Resource> resources = resourceRepoService.findCanuseByIdIn(resourceIds);
        List<String> uris = new ArrayList<>();
        for (Resource resource : resources) {
            uris.add(resource.getUri());
        }
        if(uris.size()>0){
            for (MenuDTO menu : menuDTOs) {
                if(uris.contains(menu.getUri())){
                    results.add(menu);
                }
            }
        }
        return results;
    }
    
    /**
     * 去重
     * @param results MenuDto的List集合
     */
    public static List<MenuDTO>  removeDuplicate(List<MenuDTO> results)  {
        HashSet<MenuDTO> result =new HashSet<>(results);
        results.clear();
        results.addAll(result);
        return results;
     }

    /**
     * 根据用户id获取用户角色
     * @param userId 用户id
     */
	private List<String> getUserRolesByUserId(String userId) {
		List<UserRole> userRoles = userRoleRepoService.findUserRoles(userId);
		if (CollectionUtils.isEmpty(userRoles)) {
			return new ArrayList<>();
		}
		List<String> roleIds = new ArrayList<>();
		for (UserRole userRole : userRoles) {
			roleIds.add(userRole.getRoleId());
		}
		if (CollectionUtils.isEmpty(roleIds)) {
			List<UserGroup> userGroups = userGroupRepoService.findUserGroupByUserId(userId);
			List<RoleGroup> roleGroups = null;
			for (UserGroup userGroup : userGroups) {
				roleGroups = roleGroupRepoService.findRoleGroupsByGroupId(userGroup.getGroupId());
			}
            if(roleGroups!=null && roleGroups.size()>0){
                for (RoleGroup roleGroup : roleGroups) {
                    roleIds.add(roleGroup.getRoleId());
                }
            }
		}
		return roleIds;
	}

    /**
     * 根据角色id获取资源
     * @param roleId 角色id
     */
    private List<Permission> getResourceByRole(String roleId) {
        return permissionRepoService.findByRoleId(roleId);
    }
}
