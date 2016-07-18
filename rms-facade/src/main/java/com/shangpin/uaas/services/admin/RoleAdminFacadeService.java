package com.shangpin.uaas.services.admin;

import com.shangpin.uaas.api.admin.role.RoleAdminFacade;
import com.shangpin.uaas.api.admin.role.RoleCriteria;
import com.shangpin.uaas.api.admin.role.RoleDTO;
import com.shangpin.uaas.api.admin.user.UserCriteriaDTO;
import com.shangpin.uaas.api.admin.user.UserCriteriaWithRoleDTO;
import com.shangpin.uaas.api.admin.user.UserDTO;
import com.shangpin.uaas.api.admin.user.UserWithRoleDTO;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;
import com.shangpin.uaas.convert.RoleConverter;
import com.shangpin.uaas.convert.RoleDTOConverter;
import com.shangpin.uaas.entity.*;
import com.shangpin.uaas.services.api.MemcachedUtilService;
import com.shangpin.uaas.services.dao.*;
import com.shangpin.uaas.sort.RoleComparator;
import com.shangpin.uaas.util.PageListUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 */
@Service
public class RoleAdminFacadeService implements RoleAdminFacade {
	protected static Logger log = LoggerFactory.getLogger(RoleAdminFacadeService.class);
	protected static final String CREAT_ROLE_CACHE ="1";
	protected static final String UPDATE_ROLE_CACHE ="1";
	protected static final String DELETE_ROLE_CACHE ="2";
	@Autowired
	UserRepoService userRepoService;
	@Autowired
	RoleRepoService roleRepoService;
	@Autowired
	UserRoleRepoService userRoleRepoService;
	@Autowired
	UserAdminFacadeService userAdminFacadeService;
	@Autowired
	PermissionRepoService permissionRepoService;
	@Autowired
	ResourceRepoService resourceRepoService;
	@Autowired
	FlushCacheService flushCacheService;
	@Autowired
	GroupRepoService groupRepoService;
	@Autowired
	RoleGroupRepoService roleGroupRepoService;
	@Autowired
	MemcachedUtilService memcachedUtilService;
	/**
	 * 创建角色
	 *
	 * @param role
	 *            参考DTO
	 */
	@Override
	public String createRole(RoleDTO role) {
		List<Role> ldapRoles = roleRepoService.findByName(role.getCode());
		if (!ldapRoles.isEmpty()) {
			return "角色名称重复";
			//throw new RuntimeException("角色不能重复" + role.getCode());
		}
		Role entity = RoleDTOConverter.toCreateEntity(role);
		int insert = roleRepoService.insert(entity);
		if(insert!=1){
			//return "角色创建时系统出错";
			throw new RuntimeException("角色创建失败，id:" + entity.getId());
		}
		memcachedUtilService.updateRoleToCacheByRoleId(entity.getId(),CREAT_ROLE_CACHE);
		return "success";
	}

	@Override
	public void modifyRole(RoleDTO role) {
		log.debug("测试：" + role.getId());
		Role r = roleRepoService.findById(role.getId());
		if (r == null) {
			throw new RuntimeException("没有该角色:" + role.getCode());
		}
		Role entity = RoleDTOConverter.toUpdateEntity(r, role);
		int update = roleRepoService.update(entity);
		if(update!=1){
			throw new RuntimeException("更新角色失败，id:" + entity.getId());
		}
		memcachedUtilService.updateRoleToCacheByRoleId(entity.getId(),UPDATE_ROLE_CACHE);
	}

	@Override
	public RoleDTO getRole(String roleId) {
		Role result = roleRepoService.findById(roleId);
		if (result == null) {
			throw new RuntimeException("该角色不存在" + roleId);
		}
		return RoleConverter.toRoleDTO(result);
	}

	@Override
	public RoleDTO getRoleByCode(String name) {
		List<Role> result = roleRepoService.findByName(name);
		if (result == null|| result.isEmpty()) {
			//throw new RuntimeException("该角色不存在:" + name);
			return null;
		}else{
			return RoleConverter.toRoleDTO(result.get(0));
		}
	}

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 *            必填
	 */
	@Override
	@Transactional
	public void deleteRole(String roleId) {
		flushCacheService.modify++;
		Role result = roleRepoService.findById(roleId);
		if (result == null) {
			throw new RuntimeException("该角色不存在" + roleId);
		}
		int deleteRoleId = roleRepoService.delete(roleId);
		if(deleteRoleId!=1){
			throw new RuntimeException("删除角色失败，role id：" + roleId);
		}
		List<UserRole> userRoles = userRoleRepoService.findByRoleId(roleId);
		for (UserRole userRole : userRoles) {
			int deleteUserRole = userRoleRepoService.delete(userRole.getId());
			log.debug("根据角色id共删除userRole记录数为："+deleteUserRole);
		}
		List<RoleGroup> roleGroups = roleGroupRepoService.findRoleGroupByRoleId(roleId);
		for (RoleGroup roleGroup : roleGroups) {
			int deleteGroup = groupRepoService.delete(roleGroup.getId());
			log.debug("根据角色id共删除group记录数为："+deleteGroup);
		}
		List<Permission> permissions = permissionRepoService.findByRoleId(roleId);
		for (Permission permission : permissions) {
			int deletePremisson = permissionRepoService.delete(permission.getId());
			log.debug("根据角色id共删除permission记录数为："+deletePremisson);
		}
		memcachedUtilService.updateRoleToCacheByRoleId(roleId,DELETE_ROLE_CACHE);
	}

	@Override
	public PagedList<RoleDTO> findAllRolesByCriteria(RoleCriteria roleCriteria, Paginator paginator) {
		// TODO page find
		List<Role> result = roleRepoService.findByCriteriaDto(roleCriteria,paginator);
		List<RoleDTO> roleResultDTOs = new ArrayList<>();
		for (Role role : result) {
			roleResultDTOs.add(RoleConverter.toRoleDTO(role));
		}
		RoleComparator roleComparator = new RoleComparator();
		Collections.sort(roleResultDTOs, roleComparator);
		long totalCount = roleRepoService.findCountByCriteriaDto(roleCriteria);
		return new PagedList<>(totalCount, paginator, roleResultDTOs);
	}

	/**
	 * 用户与角色的绑定
	 *
	 * @param userId
	 *            用户标识
	 * @param roleIds
	 *            角色标识 必须 0..n 需要解释0
	 */
	@Override
	@Transactional
	public void modifyRoleAndUserBindings(String userId, List<String> roleIds) {
		flushCacheService.modify++;
		log.debug("重新绑定角色");
		List<UserRole> userRoles = userRoleRepoService.findByUserId(userId);
		log.debug("该用户关联的角色数为：" + userRoles.size());
		for (UserRole userRole : userRoles) {
			userRoleRepoService.delete(userRole.getId());
		}

		User user = userRepoService.findById(userId);
		if (user == null) {
			throw new RuntimeException("没有该用户：" + userId);
		}
		for (String roleId : roleIds) {
			Role role = roleRepoService.findById(roleId);
			createUserRole(user, role);
		}
	}

	@Override
	public void enabledRole(String roleId, Boolean isEnabled) {
		flushCacheService.modify++;
		Role role = roleRepoService.findById(roleId);
		if (role == null) {
			throw new RuntimeException("没有该角色:" + roleId);
		}
		role.setStatus(isEnabled);
		int update = roleRepoService.update(role);
		if(update!=1){
			throw new RuntimeException("更新角色失败，角色id:" + roleId);
		}

		List<UserRole> userRoles = userRoleRepoService.findByRoleId(roleId);
		for (UserRole userRole : userRoles) {
			User user = userRepoService.findById(userRole.getUserId());
			if (user == null) {
				throw new RuntimeException("没有该用户" + userRole.getUserId());
			}
			createUserRole(user, role);
		}
		List<Permission> permissions = permissionRepoService.findByRoleId(roleId);
		for (Permission permission : permissions) {
			Resource resource = resourceRepoService.findById(permission.getResourceId());
			if (resource != null) {
				registerPermission(role, resource);
			} else {
				throw new RuntimeException("根据许可获取资源id查询资源出错，许可id：" + permission.getId()+"  资源id："+permission.getResourceId());
			}
		}
	}

	@Override
	public PagedList<RoleDTO> findAllRoles(Paginator paginator) {
		// TODO 分页
		List<Role> roles = roleRepoService.findAll();
		List<RoleDTO> result = new ArrayList<>(roles.size());
		for (Role role : roles) {
			result.add(RoleConverter.toRoleDTO(role));
		}

		RoleComparator roleComparator = new RoleComparator();
		Collections.sort(result, roleComparator);

		return PageListUtil.convert(paginator, result);

	}

	@Override
	public PagedList<RoleDTO> findRolesByUserId(String userId, Paginator paginator) {

		log.debug("查询该人员的所有角色：" + userId);
		List<UserRole> userRoles = userRoleRepoService.findByUserId(userId);
		List<RoleDTO> result = new ArrayList<>();
		for (UserRole userRole : userRoles) {
			Role role = roleRepoService.findById(userRole.getRoleId());
			if (role == null) {
				throw new RuntimeException("该用户关联的这个角色不存在：" + userRole.getRoleId());
			}
			result.add(RoleConverter.toRoleDTO(role));
		}
		return PageListUtil.convert(paginator, result);
	}

	@Override
	public PagedList<UserWithRoleDTO> findAllUsersWithRoleByCriteria(UserCriteriaWithRoleDTO criteria, String roleId,Paginator paginator) {
		try {
			UserCriteriaDTO userCriteriaDTO = new UserCriteriaDTO();
			BeanUtils.copyProperties(userCriteriaDTO, criteria);
			List<UserWithRoleDTO> returnList = new ArrayList<>();
			PagedList<UserDTO> result;
			if(criteria.getHasThisRole()==null){
				result = userAdminFacadeService.findAllUsersWithRoleByCriteriaAndStatusNull(userCriteriaDTO,roleId,paginator);
			}else if("true".equals(criteria.getHasThisRole().toString())){
				result = userAdminFacadeService.findAllUsersWithRoleByCriteria(userCriteriaDTO,roleId,paginator);
			}else{
				result = userAdminFacadeService.findAllUsersWithNotHaveRoleByCriteria(userCriteriaDTO,roleId,paginator);
			}
			for (UserDTO udto : result.getList()) {
				UserWithRoleDTO user = new UserWithRoleDTO();
				BeanUtils.copyProperties(user, udto);
				user.setHasThisRole(roleId.equals(udto.getRoleId()));
				returnList.add(user);
			}
			return  new PagedList<>(result.getTotalCount(), paginator, returnList);
			//return PageListUtil.convert(paginator, returnList);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return new PagedList<>();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return new PagedList<>();
		}
	}


	@Override
	public void assignRoleToUser(String roleId, String userId) {
		flushCacheService.modify++;
		log.debug("重新绑定角色");
		UserRole userRole = new UserRole();
		userRole.setId(UUID.randomUUID().toString());
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		userRole.setStatus(true);
		userRoleRepoService.insert(userRole);

	}

	/**
	 * 请出角色
	 * 
	 * @param roleId 角色id
	 * @param userId 用户id
	 */
	@Override
	public boolean revokeRoleFromUser(String roleId, String userId) {
		flushCacheService.modify++;
		return userRoleRepoService.deleteByUserIdAndRoleId(roleId, userId)==1;
	}

	/**
	 * 使用该方法前保证User和Role都是存在的
	 *
	 * @param user 用户对象
	 * @param role 角色对象
	 */
	public void createUserRole(User user, Role role) {
		List<UserRole> userRoles = userRoleRepoService.findByUserIdAndRoleId(user.getId(),role.getId());
		UserRole userRole;
		if(userRoles!=null && userRoles.size()>0){
			userRole = userRoles.get(0);
			if (userRole.getRoleId().equals(role.getId())) {
				if (user.isStatus() && role.isStatus()) {
					userRole.setStatus(true);
					userRoleRepoService.update(userRole);
				} else {
					userRole.setStatus(false);
					userRoleRepoService.update(userRole);
				}
			}
		}else{
			userRole = new UserRole();
			userRole.setId(UUID.randomUUID().toString());
			userRole.setUserId(user.getId());
			userRole.setRoleId(role.getId());
			if (user.isStatus() && role.isStatus()) {
				userRole.setStatus(true);
			} else {
				userRole.setStatus(false);
			}
			userRoleRepoService.insert(userRole);
		}
	}

	/**
	 * 绑定用户到资源
	 *
	 * 在调用该方法前保证role和resource都是存在的
	 *
	 * @param role 角色
	 * @param resource 资源
	 */
	public void registerPermission(Role role, Resource resource) {
		flushCacheService.modify++;
		List<Permission> permissionList = permissionRepoService.findByResourceIdAndRoleId(resource.getId(),role.getId());
		if(permissionList==null || permissionList.isEmpty()){
			throw new RuntimeException("根据资源id和角色id查询许可结果不存在，resourceId："+resource.getId()+"  roleId:"+role.getId());
		}
		Permission permission = permissionList.get(0);
		if (permission == null) {
			permission = new Permission();
			permission.setStatus(role.isStatus() && resource.isEnabled());
			permission.setId(UUID.randomUUID().toString());
			permission.setResourceId(resource.getId());
			permission.setRoleId(role.getId());
			permission.setUri(resource.getUri());
			permissionRepoService.insert(permission);
		} else {
			if (role.isStatus() && resource.isEnabled()) {
				permission.setStatus(true);
			} else {
				permission.setStatus(false);
			}
			permissionRepoService.update(permission);
		}
	}
}
