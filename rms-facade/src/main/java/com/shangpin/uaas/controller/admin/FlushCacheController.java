package com.shangpin.uaas.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shangpin.uaas.entity.Group;
import com.shangpin.uaas.entity.Menu;
import com.shangpin.uaas.entity.Role;
import com.shangpin.uaas.entity.User;
import com.shangpin.uaas.services.admin.FlushCacheService;
import com.shangpin.uaas.services.dao.GroupRepoService;
import com.shangpin.uaas.services.dao.MenuRepoService;
import com.shangpin.uaas.services.dao.RoleRepoService;
import com.shangpin.uaas.services.dao.UserRepoService;

import net.spy.memcached.MemcachedClient;

@RestController
@RequestMapping("/flushCache")
public class FlushCacheController {
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private UserRepoService userRepoService;
	@Autowired
	private RoleRepoService roleRepoService;
	@Autowired
	private MenuRepoService menuRepoService;
	@Autowired
	private GroupRepoService groupRepoService;
	@Autowired
	private FlushCacheService flushCacheService;

	@RequestMapping("/index")
	public String index() {
		flushCacheService.flushPermission();
		flushCacheService.flushMenu();
		return "清除用户完成";
	}

	public String flushCache() {
		flushCacheService.flushPermission();
		flushCacheService.flushMenu();
		return "清除用户完成";
	}

	@RequestMapping("/flushRoleCache")
	public String flushRoleCache() throws Exception {
		List<Role> roles = roleRepoService.findAll();
		for (Role role : roles) {
			flushSingle("EnabledPermissionByRoleId:" + role.getId());
		}
		return "清除角色完成";
	}

	@RequestMapping("/flushSingle")
	public String flushSingle(String key) {
		memcachedClient.delete(key);
		return "清除单个${key}完成";
	}

	@RequestMapping("/flushMenu")
	public String flushMenu() throws Exception {
		List<Menu> menus = menuRepoService.findByParentId("1");
		List<User> users = userRepoService.findAll();
		for (User user : users) {
			for (Menu menu : menus) {
				flushSingle("UAAS-MENU:" + user.getId() + "-" + menu.getAppCode());
			}
		}
		return "清除菜单完成";

	}

	@RequestMapping("/flushGroup")
	public String flushGroup() throws Exception {
		List<User> users = userRepoService.findAll();
		for (User user : users) {
			flushSingle("EnabledUserGroupByUser:" + user.getId());
		}
		List<Group> groups = groupRepoService.findAll();
		if (groups != null && groups.size() > 0) {
			for (Group group : groups) {
				flushSingle("EnabledRoleGroupsByGroup:" + group.getId());
			}
		}

		return "清除用户完成";
	}

}
