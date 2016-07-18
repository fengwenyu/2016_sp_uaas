package com.shangpin.uaas.services.admin;

import com.shangpin.uaas.api.admin.user.Status;
import com.shangpin.uaas.api.admin.user.UserAdminFacade;
import com.shangpin.uaas.api.admin.user.UserCriteriaDTO;
import com.shangpin.uaas.api.admin.user.UserDTO;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;
import com.shangpin.uaas.convert.UserConverter;
import com.shangpin.uaas.convert.UserDTOConverter;
import com.shangpin.uaas.entity.Organization;
import com.shangpin.uaas.entity.Role;
import com.shangpin.uaas.entity.User;
import com.shangpin.uaas.entity.UserRole;
import com.shangpin.uaas.services.dao.OrganizationRepoService;
import com.shangpin.uaas.services.dao.RoleRepoService;
import com.shangpin.uaas.services.dao.UserRepoService;
import com.shangpin.uaas.services.dao.UserRoleRepoService;
import com.shangpin.uaas.util.PageListUtil;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户服务
 *
 */
//@Transactional
@Service
public class UserAdminFacadeService implements UserAdminFacade {
	protected static Logger log = LoggerFactory.getLogger(UserAdminFacadeService.class);
//	final String LEAVE = "ou=离职人员";
	@Autowired
	UserRepoService userRepoService;
	@Autowired
	OrganizationRepoService organizationRepoService;
	@Autowired
	UserRoleRepoService userRoleRepoService;
	@Autowired
	RoleRepoService roleRepoService;
	@Autowired
	FlushCacheService flushCacheService;
	@Autowired
	MemcachedClient memcachedClient;
	//	@Override
	public String createUser(UserDTO user) {
		User entity = UserDTOConverter.toUserEntity(user);
		String leaderId = entity.getDirectLeaderId();

		if (StringUtils.isNotEmpty(leaderId)) {
			User leaderResult = userRepoService.findById(leaderId);
			if (leaderResult==null) {
				return "该人员的直属领导不存在";
				//throw new RuntimeException("该人员的直属领导不存在:" + leaderId);
			}
		}

		String organizationId = entity.getOrganizationId();
		Organization organization = organizationRepoService.findById(organizationId);
		if (organization ==null) {
			return "该人员的所在部门不存在";
			//throw new RuntimeException("该人员的所在机构不存在:" + organizationId);
		}
		String id = UUID.randomUUID().toString();
		entity.setId(id);
		int insert = userRepoService.save(entity);
		if(insert!=1){
			throw new RuntimeException("创建用户失败，用户id：" + id);
		}
		return "success";
	}

	//	@Override
	public void modifyUser(UserDTO user) {
		flushCacheService.modify++;
		User firstUser = userRepoService.findById(user.getId());

		if (firstUser==null) {
			throw new RuntimeException("该人员不存在");
		}

		log.debug("修改前的Realname:{}，org:{}",firstUser.getRealName(),firstUser.getOrganizationId());
		//String organizationId = firstUser.getOrganizationId();
		log.debug("用户所在部门：" + user.getOrganizationId());
		// 修改部门
		if (StringUtils.isNotEmpty(user.getOrganizationId()) && !"0".equals(user.getOrganizationId())
				&& !"1".equals(user.getOrganizationId())) {
			Organization organization = organizationRepoService.findById(user.getOrganizationId());
			if (organization == null) {
				throw new RuntimeException("该人员的所在机构不存在");
			}
			firstUser.setOrganizationId(organization.getId());
		}

		User entityUser = UserDTOConverter.toUpdateEntity(firstUser, user);

		userRepoService.update(entityUser);
	}

	//	@Override
	public UserDTO getUser(String userId) {
		log.debug("该用户的ID: " + userId);
		User leaderResult = userRepoService.findById(userId);
		if (leaderResult==null) {
			throw new RuntimeException("没有该人员");
		}
		return UserConverter.toUserDTO(leaderResult);
	}

	//	@Override
	public PagedList<UserDTO> findAllUsersByCriteria(UserCriteriaDTO userCriteriaDTO, Paginator paginator) {
		log.debug("开始查询所有用户");
		if (null == paginator) {
			paginator = new Paginator();
			paginator.setPage(1);
			paginator.setPageSize(10);
		}
		log.debug("============" + paginator.getPage() + " ==========" + paginator.getPageSize());

		//查询树 人员 处理开始
		if(StringUtils.isNotBlank(userCriteriaDTO.getOrganizationId())){
			Set<String> organizationIds= getAllChildOrgidByNowOriId(userCriteriaDTO.getOrganizationId(), new HashSet<String>());
			if(!organizationIds.isEmpty()){
				userCriteriaDTO.setOrganizationIds(new ArrayList<>(organizationIds));
			}
		}
		//查询树 人员结束

		List<User> userList = userRepoService.findByCriteriaDto(userCriteriaDTO,paginator);
		log.debug("result.size:" + userList.size());
		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : userList) {
			userDTOs.add(UserConverter.toUserDTO(user));
		}
		log.debug("userDTOs size" + userDTOs.size());
		long totalCount = userRepoService.findCountByCriteriaDto(userCriteriaDTO);
		return new PagedList<>(totalCount, paginator, userDTOs);
//		UserComparator userComparator = new UserComparator();
//		Arrays.sort(userDTOs., userComparator);
//		PagedList<UserDTO> pagedList = PageListUtil.convert(paginator, userDTOs);
		/*for (UserDTO userDTO : pagedList.getList()) {
			Organization organization = organizationRepoService.findById(userDTO.getOrganizationId());
			if (organization == null) {
				throw new RuntimeException("没有该机构" + userDTO.getOrganizationId());
			}
			String name = "";
			//TODO 不确定的逻辑，待定
			String names = organization.getName();
			if(names.contains(",")){
				for (String o : names.split(",")) {
					if (!o.contains("Department")) {
						name += o.split("=")[1] + "/";
					}
				}
				userDTO.setOrganizationName(name.subSequence(0, name.length() - 1).toString());
			}
		}*/
	}

	/**
	 * 查询role下的用户
	 * @param userCriteriaDTO 用户条件查询对象
	 * @param roleId 角色id
	 * @param paginator 分页对象
     */
	public PagedList<UserDTO> findAllUsersWithRoleByCriteria(UserCriteriaDTO userCriteriaDTO,String roleId, Paginator paginator) {
		if (null == paginator) {
			paginator = new Paginator();
			paginator.setPage(1);
			paginator.setPageSize(10);
		}
		List<User> userList = userRepoService.findAllUsersWithRoleByCriteria(userCriteriaDTO,roleId,paginator);
		log.debug("result.size:" + userList.size());
		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : userList) {
			userDTOs.add(UserConverter.toUserDTO(user));
		}
		log.debug("userDTOs size" + userDTOs.size());
		long totalCount = userRepoService.findCountAllUsersWithRoleByCriteria(userCriteriaDTO,roleId);
		return new PagedList<>(totalCount, paginator, userDTOs);
	}
	public PagedList<UserDTO> findAllUsersWithRoleByCriteriaAndStatusNull(UserCriteriaDTO userCriteriaDTO,String roleId, Paginator paginator) {
		if (null == paginator) {
			paginator = new Paginator();
			paginator.setPage(1);
			paginator.setPageSize(10);
		}
		List<User> userList = userRepoService.findAllUsersWithRoleByCriteriaAndStatusNull(userCriteriaDTO,roleId,paginator);
		log.debug("result.size:" + userList.size());
		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : userList) {
			userDTOs.add(UserConverter.toUserDTO(user));
		}
		log.debug("userDTOs size" + userDTOs.size());
		long totalCount = userRepoService.findCountAllUsersWithRoleByCriteria(userCriteriaDTO,null);
		return new PagedList<>(totalCount, paginator, userDTOs);
	}
	public PagedList<UserDTO> findAllUsersWithNotHaveRoleByCriteria(UserCriteriaDTO userCriteriaDTO,String roleId, Paginator paginator) {
		if (null == paginator) {
			paginator = new Paginator();
			paginator.setPage(1);
			paginator.setPageSize(10);
		}
		List<User> userList = userRepoService.findAllUsersWithNotHaveRoleByCriteria(userCriteriaDTO,roleId,paginator);
		log.debug("result.size:" + userList.size());
		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : userList) {
			userDTOs.add(UserConverter.toUserDTO(user));
		}
		log.debug("userDTOs size" + userDTOs.size());
		long totalCount = userRepoService.findCountAllUsersWithNotHaveRoleByCriteria(userCriteriaDTO,roleId);
		return new PagedList<>(totalCount, paginator, userDTOs);
	}

	//	@Override
	public void modifyEnable(String userId, Status status) {
		flushCacheService.modify++;
		User user = userRepoService.findById(userId);
		if (user==null) {
			throw new RuntimeException("没有该人员：" + userId);
		}

		user.setStatus(Status.ENABLED.equals(status));
		userRepoService.update(user);
		//如果用户禁用，那么就清除掉用户的token
		if(!user.isStatus()) {
			String token = (String) memcachedClient.get(userId);
			if(StringUtils.isNotBlank(token)){
				memcachedClient.delete(token);
				memcachedClient.delete(userId);
			}
		}
		List<UserRole> userRoles = userRoleRepoService.findByRoleId(userId);
		for (UserRole userRole : userRoles) {
			Role role = roleRepoService.findById(userRole.getRoleId());
			createUserRole(user, role);
		}
	}

	//	@Override
	public void deleteUser(String userId) {
		flushCacheService.modify++;
		User user = userRepoService.findById(userId);
		if (user==null) {
			throw new RuntimeException("没有该用户:" + userId);
		}
		UserDTO userDTO = UserConverter.toUserDTO(user);
		userDTO.setOrganizationId("123456");
		modifyUser(userDTO);

		List<UserRole> userRoles = userRoleRepoService.findByRoleId(userId);
		for (UserRole userRole : userRoles) {
			userRoleRepoService.delete(userRole.getId());
		}
		// userRepoService.moveUser(user.getDn().toString(), "uid=" +
		// user.getUserCode() + "," + LEAVE + "," + BASE_DN)
	}

	//	@Override
	public UserDTO getUserByUserCode(String userCode) {

		List<User> users = userRepoService.findByUserCode(userCode);
		if (users.isEmpty()) {
			return null;
		}

		User user = users.get(0);
		return UserConverter.toUserDTO(user);
	}

	//	@Override
	public UserDTO getUserByUsername(String username) {
		log.debug("查询用户名：" + username);
		List<User> users = userRepoService.findByUsername(username);
		if (users.isEmpty()) {
			return null;
		}

		User user = users.get(0);
		return UserConverter.toUserDTO(user);
	}

	//	@Override
	public PagedList<UserDTO> findAllTreeUsersByOrganizationId(String organizationId, Paginator paginator) {
		Organization organization = organizationRepoService.findById(organizationId);
		if (organization == null) {
			return PageListUtil.convert(paginator, new ArrayList<UserDTO>());
		}

		List<User> users = userRepoService.findByOrganizationId(organization.getId());
		List<UserDTO> result = new ArrayList<>();
		for (User user : users) {
			result.add(UserConverter.toUserDTO(user));
		}
		return PageListUtil.convert(paginator, result);
	}

	/**
	 * 使用该方法前保证User和Role都是存在的
	 *
	 * @param user 用户
	 * @param role 角色
	 */
	private void createUserRole(User user, Role role) {
		log.debug("========user.status" + user.isStatus() + "========role.status" + role.isStatus());
		List<UserRole> userRoles = userRoleRepoService.findByRoleId(user.getId());

		for (UserRole userRole : userRoles) {
			log.debug("========user.uuid" + user.getId() + "========role.uuid" + role.getId() + "绑定的"
					+ userRole.getRoleId());
			if (userRole.getRoleId().equals(role.getId())) {
				if (user.isStatus() && role.isStatus()) {
					userRole.setStatus(true);
					userRoleRepoService.update(userRole);
					return;
				} else {
					userRole.setStatus(false);
					userRoleRepoService.update(userRole);
					return;
				}
			}
		}

		log.debug("创建用户角色user.uuid：" + user.getId() + ", role.uuid" + role.getId());
		UserRole userRole = new UserRole();
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

	//	@Override
	public boolean verifyUserName(String userId, String userName) {
		List<User> users = userRepoService.findByUsername(userName);
		if (users==null || users.size()==0 ||users.size() > 2) {
			return false;
		}
		String id = users.get(0).getId();
		return id.equals(userId);
	}

	/**
	 * 根据生日获取年龄
	 * @param date 生日 yyyy-MM-dd
     */
	public String getUserAge(String date) {
		String[] split = date.split("-");
		Calendar birthday = new GregorianCalendar(Integer.parseInt(split[0]), Integer.parseInt(split[1])-1,Integer.parseInt(split[2]));//month从0开始

		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
		int month = now.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);
		int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
		//按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。

		if(day<0){
			month -= 1;
			now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
			//day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		if(month<0){
			//month = (month+12)%12;
			year--;
		}
		return year+"";
	}

	public Set<String> getAllChildOrgidByNowOriId(String orgId,Set<String> orgIds){
		orgIds.add(orgId);
		//查询出所有的子节点
		List<Organization> byParentId = organizationRepoService.findByParentId(orgId);
		for (Organization organization : byParentId) {
			getAllChildOrgidByNowOriId(organization.getId(),orgIds);
		}
		return orgIds;
	}
}
