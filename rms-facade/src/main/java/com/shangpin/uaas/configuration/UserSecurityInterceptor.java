package com.shangpin.uaas.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shangpin.uaas.api.facade.auth.dto.RoleDTO;
import com.shangpin.uaas.entity.Permission;
import com.shangpin.uaas.entity.ResourceNode;
import com.shangpin.uaas.entity.Role;
import com.shangpin.uaas.services.api.AuthenticateFacadeService;
import com.shangpin.uaas.services.api.UserFacadeService;
import com.shangpin.uaas.services.dao.PermissionRepoService;
import com.shangpin.uaas.services.dao.ResourceNodeRepoService;
import com.shangpin.uaas.services.dao.ResourceRepoService;
import com.shangpin.uaas.services.dao.RoleRepoService;
/**
 * 拦截未登录的用户信息
 * @author lance
 * 2014-6-10下午9:57:20
 */
public class UserSecurityInterceptor implements HandlerInterceptor {
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
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		//验证用户是否登陆
		boolean hasRole = false;
		Cookie[] cookies = request.getCookies();
		if (null == cookies || cookies.length < 1) {
			response.sendRedirect(request.getContextPath()+"/login.html");
			return hasRole;
		}
		String token = "";
		for (Cookie uaasCookie : cookies) {
			if (uaasCookie.getName().equals("UAAS_TOKEN")) {
				Cookie cookieToken = new Cookie("UAAS_TOKEN", uaasCookie.getValue());
				cookieToken.setPath("/");
				cookieToken.setDomain("shangpin.com");
				cookieToken.setMaxAge(30 * 60);
				token = uaasCookie.getValue();
				response.addCookie(cookieToken);
			}
		}
		if (token == "" || !authenticateFacadeService.isValid(token)) {
			throw new RuntimeException("该访问令牌无效或者已过期！");
		}
		authenticateFacadeService.touch(token);
		List<RoleDTO> roles = userFacadeService.findAllRolesByToken(token);
		List<ResourceNode> resourceNodes=resourceNodeRepoService.findByModuleNameLike("UAAS");
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
		}
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
