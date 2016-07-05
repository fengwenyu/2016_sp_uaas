package com.shangpin.uaas.services.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.shangpin.uaas.services.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.shangpin.uaas.api.facade.auth.dto.MenuDTO;
import com.shangpin.uaas.api.facade.e.APPCode;
import com.shangpin.uaas.convert.api.MenuConverter;
import com.shangpin.uaas.entity.Menu;
import com.shangpin.uaas.entity.Permission;
import com.shangpin.uaas.entity.RoleGroup;
import com.shangpin.uaas.entity.UserGroup;
import com.shangpin.uaas.entity.UserRole;

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
    private GroupRepoService groupRepoService;

    void serviceMethod() {

    }
  //@TODO
  // @Cacheable(value = "defaultCache" , key = "'UAAS-TOKEN:' + #userId")
    public List<Permission> getAllPermissionByUserId(String userId) {
        List<Permission> permissions = permissionRepoService.findByUserId(userId);
        return permissions;

    }

   // @Cacheable(value = "defaultCache" , key = "'UAAS-MENU:' + #userId + '-' + #appCode")
    public List<MenuDTO> findMenusByUserIdAndAppCode(String userId, String appCode) {

        List<MenuDTO> menuDTOs = new ArrayList<MenuDTO>();
        List<Menu> menus = menuRepoService.findByAppCode(appCode);
        for (Menu menu : menus) {
            menuDTOs.add(MenuConverter.convert(menu));
        }
        List<MenuDTO> results = new ArrayList<MenuDTO>();
        log.debug("最终需要认证的菜单数为："+menuDTOs.size());
        List<Permission> permissionDTOs = getAllPermissionByUserId(userId);
        for (MenuDTO menu : menuDTOs) {
            for (Permission permissionDTO : permissionDTOs) {
                if (menu.getUri().equals(permissionDTO.getUri())) {
                    results.add(menu);
                }
            }
	    }
        return removeDuplicate(results);
    }
    
    /**
     * 去重
     * @param results
     * @return
     */
    public static List<MenuDTO>  removeDuplicate(List<MenuDTO> results)  {
        HashSet<MenuDTO> result =new  HashSet<MenuDTO>(results);
        results.clear();
        results.addAll(result);
        return results;
     } 

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

    private List<Permission> getResourceByRole(String roleId) {
        List<Permission> perms = permissionRepoService.findByRoleId(roleId);
        return perms;
    }
}
