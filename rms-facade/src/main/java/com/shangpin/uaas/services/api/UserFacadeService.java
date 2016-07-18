package com.shangpin.uaas.services.api;

import com.shangpin.uaas.api.facade.auth.dto.GroupDTO;
import com.shangpin.uaas.api.facade.auth.dto.MenuDTO;
import com.shangpin.uaas.api.facade.auth.dto.OrganizationDTO;
import com.shangpin.uaas.api.facade.auth.dto.RoleDTO;
import com.shangpin.uaas.api.facade.user.Subject;
import com.shangpin.uaas.api.facade.user.UserDTO;
import com.shangpin.uaas.api.facade.user.UserFacade;
import com.shangpin.uaas.convert.api.*;
import com.shangpin.uaas.entity.*;
import com.shangpin.uaas.services.admin.PermissionApiFacadeService;
import com.shangpin.uaas.services.dao.*;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
 */
@Service
public class UserFacadeService implements UserFacade {
	protected static Logger log = LoggerFactory.getLogger(UserFacadeService.class);
    //final String ORGANIZATION_BASE_DN = "ou=Department";
    @Autowired
    MemcachedClient memcachedClient;
    @Autowired
    UserRepoService userRepoService;
    @Autowired
    OrganizationRepoService organizationRepoService;
    @Autowired
    RoleRepoService roleRepoService;
    @Autowired
    UserRoleRepoService userRoleRepoService;
    @Autowired
    MenuRepoService menuRepoService;
    @Autowired
    AuthenticateFacadeService authenticateFacadeService;
    @Autowired
    AuthorizationFacadeService authorizationFacadeService;
    @Autowired
    GroupRepoService groupRepoService;
    @Autowired
    PermissionApiFacadeService permissionApiFacadeService;
    @Autowired
    UserGroupRepoService userGroupRepoService;
    @Autowired
    ResourceRepoService resourceRepoService;

    @Override
   public UserDTO getUser(String token) {
        log.debug("访问令牌：{}获取当前用户！",token);
        log.debug("＝＝＝＝开始调用获取当前用户该方法＝＝＝＝");
        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }
        Subject subject = (Subject) memcachedClient.get(token);

        log.debug("============:" + subject.getUserId());
        User user =  userRepoService.findById(subject.getUserId());
        if (user ==null) {
            log.error("没有该用户：" + subject.getUserId());
            throw new RuntimeException("没有该用户！");
        }

        if (!user.isStatus()) {
            log.error("该用户被禁用:" + subject.getUserId());
            throw new RuntimeException("该用户被禁用！");
        }
       return convert(user);
    }

    @Override
   public OrganizationDTO findOrganizationByToken(String token) {
        log.debug("访问令牌：${token}获取当前用户所在部门！");
        UserDTO userDTO = getUser(token);
        return userDTO.getOrganizationDTO();
    }

    @Override
    public List<RoleDTO> findAllRolesByToken(String token) {
        log.debug("访问令牌：${token}获取当前用户的有效角色！");
        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }

        Subject subject = (Subject) memcachedClient.get(token);
        List<Role> roles = roleRepoService.findByUserId(subject.getUserId());
        List<RoleDTO> roleDTOs = new ArrayList<>();
        if(roles!=null && roles.size()>0){
            for (Role role : roles) {
                roleDTOs.add(RoleConverter.convert(role));
            }
        }
        return roleDTOs;

    }
    public List<String> findAllResourcesByToken(String token) {
        log.debug("访问令牌：${token}获取当前用户的有效角色！");
        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }
        Subject subject = (Subject) memcachedClient.get(token);
        List<Resource> resources = resourceRepoService.findResourcesByUserId(subject.getUserId());
        List<String> urls = new ArrayList<>();
        for (Resource resource : resources) {
            String uri = resource.getUri();
            if(StringUtils.isNotBlank(uri)){
                if(uri.startsWith("mvc-action://")){
                    String replace = uri.replace("mvc-action://", "/");
                    urls.add(replace);
                }else{
                    urls.add(uri);
                }
            }
        }
        return urls;
    }

    @Override
    public List<MenuDTO> findMenusByAppCode(String token, String appCode) {
        log.debug("findMenusByAppCode：" + token+":appCode:"+appCode);
        Subject subject = (Subject) memcachedClient.get(token);

        return permissionApiFacadeService.findMenusByUserIdAndAppCode(subject.getUserId(), appCode);
//        return findMenusByUserIdAndAppCode(subject.userId, appCode, token)
    }

    @Override
    public List<MenuDTO> findTopMenusByToken(String token) {
        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }
        //获取所有的目录节点menu
        List<MenuDTO> menuDTOs = new ArrayList<>();
        List<Menu> topMenus = menuRepoService.findByParentId("1");
        for (Menu subMenu : topMenus) {
        	 menuDTOs.add(MenuConverter.convert(subMenu));
		}

        List<MenuDTO> results = new ArrayList<>();
        for (MenuDTO menu : menuDTOs) {
        	 log.debug("菜单认证权限的URI:" + menu.getUri());
            //校验该用户是否有首页menu的权限
             if (authorizationFacadeService.isPermitted(token, menu.getUri())) {
                 results.add(menu);
             }
		}
        return results;
    }

    @Override
    public UserDTO getUserByTokenAndUserId(String token, String userId) {
        log.debug("访问令牌：${token}获取其他用户信息${userId}");
        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }

        User user = userRepoService.findById(userId);
        if (user==null) {
            log.error("没有该用户：" + userId);
        }

        return convert(user);
    }

    @Override
    public List<OrganizationDTO> findOrganizationsByTokenAndParentId(String token, String parentId) {
        log.debug("访问令牌：${token}获取其他用户信息${parentId}");

        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }
        List<Organization> organizations = organizationRepoService.findByParentId(parentId);
        List<OrganizationDTO> organizationDTOs=new ArrayList<>();
        for (Organization organization : organizations) {
        	organizationDTOs.add(OrganizationConverter.convert(organization));
		}
        return organizationDTOs;
    }

    @Override
    public List<OrganizationDTO> findAllOrganizationsByToken(String token) {
    	if (!authenticateFacadeService.isValid(token)) {
		    throw new RuntimeException("该访问令牌过期或无效！");
		}
		List<Organization> organizations = organizationRepoService.findAll();
		List<OrganizationDTO> result=new ArrayList<>();
		for (Organization organization : organizations) {
			result.add(OrganizationConverter.convert(organization));
		}
		return result;
    }

    @Override
    public List<UserDTO> findUsersByOrganizationId(String token, String organizationId) {
        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }

        List<User> users = userRepoService.findByOrganizationId(organizationId);
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
        	 userDTOs.add(convert(user));
		}
        return userDTOs;
    }

    @Override
    public List<UserDTO> findUsersByRoles(String token, List<String> roleIds) {
        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }
        List<UserDTO> userDTOs = new ArrayList<>();
        List<UserRole> allUserRoles = new ArrayList<>();
        if (null == roleIds || roleIds.isEmpty()) {
            return userDTOs;
        }
        for (String roleId : roleIds) {
        	 List<UserRole> userRoles = userRoleRepoService.findByRoleId(roleId);
        	 if (!userRoles.isEmpty()) {
                 allUserRoles.addAll(userRoles);
             }
		}

        Set<String> userIds = new HashSet<>();
        for (UserRole userRole : allUserRoles) {
        	  userIds.add(userRole.getUserId());
		}
        for (String it : userIds) {
        	  User user = userRepoService.findById(it);
        	  if (user!=null) {
                  userDTOs.add(convert(user));
              }
		}
        return userDTOs;
    }

    @Override
    public List<UserDTO> findUsersByGroup(String token, String groupId) {
        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }
        List<UserGroup> userGroups = userGroupRepoService.findUserGroupByGroupId(groupId);
        List<User> users = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
        	 User users1 = userRepoService.findById(userGroup.getUserId());
        	 if (users1!=null) {
                 users.add(users1);
             }
		}
        List<UserDTO> result=new ArrayList<>();
        for (User user : users) {
        	result.add(convert(user));
		}
        return result;
    }
///????
  /*  @Override
    public List<GroupDTO> findGroupsByUser(String token, String userCode) {
        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }
        def groups = groupRepoService.findEnabledUserGroupByUserCode(userCode);
        def result = groups.collect { group ->
            use (GroupConverter) {
                group.convert();
            }
        }
        return result;
    }
*/
   @Override
   public List<GroupDTO> findAllGroups(String token) {
        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }

        List<Group> groups = groupRepoService.findAll();
        List<GroupDTO> result=new ArrayList<>();
        for (Group group : groups) {
        	result.add(GroupConverter.convert(group));
		}
        return result;
    }

   public List<MenuDTO> findMenusByUserIdAndAppCode(String userId, String appCode, String token) {

        if (!authenticateFacadeService.isValid(token)) {
            throw new RuntimeException("该访问令牌过期或无效！");
        }

        List<MenuDTO> menuDTOs = new ArrayList<>();
        List<Menu> menus = menuRepoService.findByAppCodeAndParentId(appCode, "1");

        log.debug("该系统一共得菜单数为：${menus.size()}");
        if (menus.isEmpty()) {
            log.error("该应用没有菜单:" + appCode);
        } else {
            Menu menu = menus.get(0);
            //TODO while
            List<Menu> subMenus = menuRepoService.findByParentId(menu.getId());

            log.debug("该系统自子菜单一共得菜单数为：${subMenus.size()}");
            for (Menu subMenu : subMenus) {
            	menuDTOs.add(MenuConverter.convert(subMenu));
			}
        }

        List<Menu> topMenus = menuRepoService.findByParentId("1");
        for (Menu menu2 : topMenus) {
        	 if (!appCode.equals(menu2.getAppCode())) {
                 menuDTOs.add(MenuConverter.convert(menu2));
             }
		}

        List<MenuDTO> results = new ArrayList<>();

        log.debug("最终需要认证的菜单数为：${menuDTOs.size()}");
        for (MenuDTO menu : menuDTOs) {
        	log.debug("菜单认证权限的URI:" + menu.getUri());
            if (authorizationFacadeService.isPermitted(token, menu.getUri())) {
                results.add(menu);
            }
		}
        return results;
    }

    private UserDTO convert(User user) {

        UserDTO userDTO=UserConverter.convert(user);


        Organization organization = organizationRepoService.findById(user.getOrganizationId());

        if (null==organization) {
            log.error("该员工（${user.userCode}）没有行政部门或者该部门（${user.organizationId}）不存在！");
        } else {
            OrganizationDTO organizationDTO= OrganizationConverter.convert(organization);
            userDTO.setOrganizationDTO(organizationDTO);
        }
        List<UserRole> userRoles = userRoleRepoService.findByUserId(user.getId());
        for (UserRole userRole : userRoles) {
        	//TODO use in
        	Role role = roleRepoService.findById(userRole.getRoleId());
        	 if (role == null) {
                 log.error("该角色不存在或者被删除／禁用" + userRole.getRoleId());
             } else {
                 userDTO.getRoles().add(RoleConverter.convert(role));
             }
		}
        return userDTO;
    }

    @Override
    public Map<String,String> changePassword(String token, String oldPassword, String newPassword) {
    	Map<String,String> map=new HashMap<>();
        if (!authenticateFacadeService.isValid(token)) {
        	map.put("code", "0");
        	map.put("msg", "该访问令牌过期或无效！");
            return map;
        }
        Subject subject = (Subject)authenticateFacadeService.memcachedClient.get(token);
        List<User> users = userRepoService.findByUserCode(subject.getUserCode());
        if(users.isEmpty() || users.size() > 1){
        	map.put("code", "0");
        	map.put("msg", "该用户不存在或信息不正确！");
            return map;
        }
        User user = users.get(0);
        if(user.getPassword() !=  DigestUtils.md5DigestAsHex(oldPassword.getBytes())){
        	map.put("code", "0");
        	map.put("msg", "原密码不正确！");
            return map;
        }
        user.setPassword(  DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        userRepoService.update(user);
    	map.put("code", "1");
    	map.put("msg", "修改成功!");
        return map;
    }

	@Override
	public List<GroupDTO> findGroupsByUser(String token, String userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
