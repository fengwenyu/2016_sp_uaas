package com.shangpin.uaas.configuration;

import com.shangpin.uaas.api.facade.user.Subject;
import com.shangpin.uaas.services.api.AuthenticateFacadeService;
import com.shangpin.uaas.services.api.MemcachedUtilService;
import com.shangpin.uaas.services.api.UserFacadeService;
import com.shangpin.uaas.services.dao.PermissionRepoService;
import com.shangpin.uaas.services.dao.ResourceNodeRepoService;
import com.shangpin.uaas.services.dao.ResourceRepoService;
import com.shangpin.uaas.services.dao.RoleRepoService;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 拦截请求，校验用户是否有权限
 * @author lance
 * 2014-6-10下午9:57:20
 */
@Component
public class ResourceInterceptor implements HandlerInterceptor {
	protected static Logger log = LoggerFactory.getLogger(ResourceInterceptor.class);
	@Autowired
	private AuthenticateFacadeService authenticateFacadeService;
	@Autowired
	private UserFacadeService userFacadeService;
	@Autowired
	private PermissionRepoService permissionRepoService;
	@Autowired
	private ResourceRepoService resourceRepoService;

	@Autowired
	private ResourceNodeRepoService resourceNodeRepoService;

	@Autowired
	private RoleRepoService roleRepoService;
	@Autowired
	private MemcachedUtilService memcachedUtilService;

	@Autowired
	MemcachedClient memcachedClient;

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler){
		log.info("請求地址:"+request.getRequestURI()+"  --> param: "+request.getQueryString());
		//request.getParameter("params");
		try {
			//验证用户是否登陆
			boolean hasRole = false;
			Cookie[] cookies = request.getCookies();
			if (null == cookies || cookies.length < 1) {
				response.sendRedirect(request.getContextPath() + "/login.html");
				log.info("cookies没有值");
				return false;
			}
			String token = "";
			for (Cookie uaasCookie : cookies) {
				if (uaasCookie.getName().equals("UAAS_TOKEN")) {
					Cookie cookieToken = new Cookie("UAAS_TOKEN", uaasCookie.getValue());
					cookieToken.setPath("/");
					cookieToken.setDomain("shangpin.com");
					cookieToken.setMaxAge(30*60*60);
					token = uaasCookie.getValue();
					response.addCookie(cookieToken);
					hasRole = true;
				}
			}
			if (!hasRole) {//用户未登录
				response.sendRedirect(request.getContextPath() + "/login.html");
			}
			if (token == "" || !authenticateFacadeService.isValid(token)) {
				throw new RuntimeException("该访问令牌无效或者已过期！");
			}
			String roleKey = "resource:uri:" + token;
			List<String> roleIds = (List<String>) memcachedClient.get(roleKey);
			boolean hasPermission = false;
			String requestURI = request.getRequestURI();
			log.info("验证url是否是facade");
			if(requestURI.startsWith("/facade/json/com.shangpin.uaas.api/")){
				return true;
			}
			if (!roleIds.isEmpty()) {
				Map<String, List<String>> roleFromCache = memcachedUtilService.getAllRoleFromCache();
				for (String roleId : roleIds) {
					List<String> resourcesUri = roleFromCache.get(roleId);
					if (resourcesUri.contains(requestURI)||resourcesUri.contains("UAAS")) {
						hasPermission = true;
						break;
					}
				}
			}
			authenticateFacadeService.touch(roleKey);
			if (hasPermission) {
				return true;
			} else {
				response.setStatus(403);
				throw new RuntimeException("该用户没有权限访问该权限！");
			}
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}

		/*List<ResourceNode> resourceNodes=resourceNodeRepoService.findByModuleNameLike("UAAS");
		if (CollectionUtils.isNotEmpty(resourceNodes)) {
			String resourceId= resourceNodes.get(0).getResourceId();
			List<Permission> permissions = permissionRepoService.findByResourceId(resourceId);
			List<Role> roless=new ArrayList<>();
			for (Permission permission : permissions) {
				roless.add(roleRepoService.findById(permission.getRoleId()));
			}
			for (RoleDTO rDto : roles) {
				for (Role role : roless) {
					if ( rDto.getCode().equals(role.getName())) {
						hasRole=true;
						break;
					}
				}
			}
		}
		for (RoleDTO rDto : roles) {
			if ("admin".equals(rDto.getCode())) {
				hasRole = true;
			}
		}
		if (!hasRole) {
			response.setStatus(403);
			throw new RuntimeException("该用户没有权限访问控制台！");
		} else {
			return true;
		}*/
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
