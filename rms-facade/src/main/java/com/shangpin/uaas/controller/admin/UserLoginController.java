package com.shangpin.uaas.controller.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shangpin.uaas.api.facade.user.UserDTO;
import com.shangpin.uaas.services.api.AuthenticateFacadeService;
import com.shangpin.uaas.services.api.UserFacadeService;
import com.shangpin.uaas.services.dao.UserRepoService;
@Controller
public class UserLoginController {
	protected static Logger log = LoggerFactory.getLogger(UserRepoService.class);
	private String jcaptchaService;
	@Autowired
	private AuthenticateFacadeService authenticateFacadeService;
	@Autowired
	private UserFacadeService userFacadeService;
	
	@RequestMapping("/")
	public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String client_url = request.getParameter("client_url");


		Cookie cookies = null;
		if (request.getCookies()!=null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("UAAS_TOKEN")) {
					cookies = cookie;
					break;
				}
			}
		}
		if (cookies != null) {
			if (authenticateFacadeService.isValid(cookies.getValue())) {
				if (!StringUtils.isEmpty(client_url)) {
					response.sendRedirect(client_url + "?token=" + cookies.getValue());
				} else {
					response.sendRedirect("/nav.html?token=" + cookies.getValue());
				}
			}
		}
		if (!StringUtils.isEmpty(client_url)) {
			response.sendRedirect("/login.html?client_url=" + client_url);
		} else {
			response.sendRedirect("/login.html");
		}
	}
	@ResponseBody
	@RequestMapping("/userLogin/login")
    public Map<String, Object> login(String username,String password,HttpServletResponse response,HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
        try {
        	Cookie[] cookies = request.getCookies();
        	
        	String token = authenticateFacadeService.login(username,password);
        	Cookie cookieToken = new Cookie("UAAS_TOKEN", token);
        	Cookie userToken = new Cookie("UAAS_USER",username);
            cookieToken.setPath("/");
            //cookieToken.setDomain("shangpin.com");
            userToken.setPath("/");
            cookieToken.setMaxAge(300 * 600);
           // userToken.setDomain("shangpin.com");
            response.addCookie(cookieToken);
            response.addCookie(userToken);
            UserDTO userDTO = userFacadeService.getUser(token);

            Cookie cookieUserID = new Cookie("UAAS_USER_ID", userDTO.getId());
            cookieUserID.setPath("/");
           // cookieUserID.setDomain("shangpin.com");
            response.addCookie(cookieUserID);
            String address = getIpAddr(request);
            log.warn("===${userDTO.realName}在${new Date()}来自于IP地址${address}登录(${userDTO.username})===");

            Cookie cookieUserRealName= new Cookie("UAAS_USER_REALNAME", URLEncoder.encode(userDTO.getRealName(), "utf-8"));
			cookieUserRealName.setPath("/");
			//cookieUserRealName.setDomain("shangpin.com");
			response.addCookie(cookieUserRealName);
			log.warn("==========添加后的Token值为：${cookieToken.value}===========");
			map.put("code", "1");
			map.put("msg", "登录成功");
			map.put("data", token); 
			return map;
        }catch (UnsupportedEncodingException e) {
        	  e.printStackTrace();
              map.put("code", "0");
              return map;
        } catch (RuntimeException e) {
            e.printStackTrace();
            map.put("code", "0");
            return map;
        }
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /***
     * 注销=
     * @param request
     * @param response
     */
    @ResponseBody
	@RequestMapping("/userLogin/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
        log.debug("=========注销===========");
        // TODO 首先清除Cookie
        String token = "";
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
	        for (Cookie cookie : cookies) {
	        	 log.info("============注销键值：${it.name}==============");
	             if (cookie.getName().equals("UAAS_TOKEN")) {
	                 token = cookie.getValue();
	                 break;
	             }
			}
        }
        Cookie cookie = new Cookie("UAAS_TOKEN", " ");//这边得用"",不能用null
        cookie.setPath("/");//设置成跟写入cookies一样的
        cookie.setDomain("shangpin.com");//设置成跟写入cookies一样的
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        // TODO 其次清除Memcached中的Key
        if (!StringUtils.isEmpty(token)) {
            authenticateFacadeService.logout(token);
        }
        String address = getIpAddr(request);
        String clientUrl = request.getParameter("client_url");
        log.warn("==========注销client_url:${clientUrl}==${address}==============");

        if (StringUtils.isEmpty(clientUrl)) {
			response.sendRedirect("/login.html?date="+System.currentTimeMillis());
        }else{
			response.sendRedirect(clientUrl);
			return;
        }
    }
}
