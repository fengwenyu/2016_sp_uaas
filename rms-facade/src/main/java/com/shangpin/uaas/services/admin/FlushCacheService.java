package com.shangpin.uaas.services.admin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangpin.uaas.api.facade.e.APPCode;
import com.shangpin.uaas.entity.Menu;
import com.shangpin.uaas.entity.User;
import com.shangpin.uaas.services.dao.MenuRepoService;
import com.shangpin.uaas.services.dao.UserRepoService;

import net.spy.memcached.MemcachedClient;

@Service
public class FlushCacheService {
	protected static Logger log = LoggerFactory.getLogger(FlushCacheService.class);
	@Autowired
	UserRepoService userRepoService;
	@Autowired
	PermissionApiFacadeService permissionApiFacadeService;
	@Autowired
	MenuRepoService menuRepoService;
	@Autowired
	MemcachedClient memcachedClient;
    int modify;
    private int finished;

    public FlushCacheService() {
        log.debug("初始化缓存。。。");
    }

    /*void serviceMethod() {

    }*/

    synchronized void flushCache() {
        log.debug("调用刷新缓存。。。");
        modify = modify % 100;
        if (modify == finished) {
            return;
        }
        log.debug("开始刷新缓存。。。");
        finished = modify;
        flushPermission();
        flushMenu();
    }

    public void flushPermission() {
    	List<User> users = userRepoService.findAll();
		for (User user : users) {
			clearCache("UAAS-TOKEN:" + user.getId());
		}
    }

    public void flushMenu() {
    	List<User> users = userRepoService.findAll();
		List<Menu> menus = menuRepoService.findByParentId("1");
		for (User user : users) {
			for (Menu menu : menus) {
				clearCache("UAAS-MENU:${user.uuid}-${menu.appCode}");
                loadMenu(user.getId(), menu.getAppCode());
			}
		}
    }

    public void clearCache(String key) {
        memcachedClient.delete(key);
    }

    public void loadPermission(String userId) {
        permissionApiFacadeService.getAllPermissionByUserId(userId);
    }

    public void loadMenu(String userId, String appCode) {
        permissionApiFacadeService.findMenusByUserIdAndAppCode(userId, appCode);
    }

}
