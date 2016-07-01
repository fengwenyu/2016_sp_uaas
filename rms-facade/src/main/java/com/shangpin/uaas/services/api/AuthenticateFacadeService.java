package com.shangpin.uaas.services.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.shangpin.uaas.entity.Role;
import com.shangpin.uaas.services.dao.RoleRepoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shangpin.uaas.api.facade.auth.AuthenticationFacade;
import com.shangpin.uaas.api.facade.user.Subject;
import com.shangpin.uaas.entity.User;
import com.shangpin.uaas.services.dao.UserRepoService;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
/**
 */
@Transactional
@Service
public class AuthenticateFacadeService implements AuthenticationFacade {
	protected static Logger log = LoggerFactory.getLogger(AuthenticateFacadeService.class);
	@Autowired
	MemcachedClient memcachedClient;
	@Autowired
	UserRepoService userRepoService;
    @Autowired
    private RoleRepoService roleRepoService;

    public String login(String userLogin, String password) {

        log.info("登录用户名为:" + userLogin);

        password = org.springframework.util.DigestUtils.md5DigestAsHex(password.getBytes());
        List<User> users = userRepoService.findByUsername(userLogin);
        if (users.isEmpty()) {
            throw new RuntimeException("没有该用户" + userLogin);
        }

        User user = users.get(0);

        if (!user.isStatus()) {
            throw new RuntimeException("该用户已禁用！");
        }

        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("该用户输入密码不正确！");
        }

        String token = UUID.randomUUID().toString();

        Subject subject = new Subject();
        subject.setToken(token);
        subject.setUserId(user.getId());
        subject.setUsername(user.getUsername());
        subject.setUserCode(user.getUserCode());

        log.debug("用户" + userLogin + "登录成功！");
        log.debug("超时时间：${config.get('timeout')}");
        memcachedClient.set(token, 60*60*30, subject);
        //将用户的授权信息存入缓存
        String resourceKey = "resource:uri:"+token;
        List<Role> roles = roleRepoService.findByUserId(user.getId());
        List<String> roleIds = new ArrayList<>();
        for (Role role : roles) {
            roleIds.add(role.getId());
        }
        memcachedClient.set(resourceKey, 60*60*30, roleIds);


        //  log.debug("------------" + ((Subject) memcachedClient.get(token)).userCode);
        return token;
    }

    public void touch(String token) {
        log.info("重新计时：" + token);
        OperationFuture<Boolean> result = memcachedClient.touch(token, 60*60*30);
        try {
			if (!result.get()) {
			    throw new RuntimeException("延长时效没有成功！");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public Boolean isValid(String token) {

    	Object obj=memcachedClient.get(token);
        Subject subject = (Subject)obj; 
        if (null == subject) {
            log.warn("该令牌无效或者过期:" + token);
            return false;
        }
        log.debug("验证有效：" + token);
        return true;
    }

    public void logout(String token) {
        memcachedClient.delete(token);
    }
}
