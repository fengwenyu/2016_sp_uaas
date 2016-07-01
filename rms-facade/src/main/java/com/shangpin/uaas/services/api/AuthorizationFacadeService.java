package com.shangpin.uaas.services.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shangpin.uaas.api.facade.auth.AuthorizationFacade;
import com.shangpin.uaas.api.facade.user.Subject;
import com.shangpin.uaas.entity.Permission;
import com.shangpin.uaas.services.admin.PermissionApiFacadeService;

import net.spy.memcached.MemcachedClient;

/**
 *
 */ 
@Transactional
@Service
public class AuthorizationFacadeService implements AuthorizationFacade {
	protected static Logger log = LoggerFactory.getLogger(AuthorizationFacadeService.class);
    private static final String NAMESPACE = "uaas";
    @Autowired
    MemcachedClient memcachedClient;
    @Autowired
    AuthenticateFacadeService authenticateFacadeService;
    @Autowired
    PermissionApiFacadeService permissionApiFacadeService;

   @Override
   @Transactional(propagation = Propagation.NOT_SUPPORTED)
   public boolean isPermitted(String token, String resourceUri) {

        log.debug("验证：" + token + "是否有" + resourceUri + "权限");
        Subject subject = getSubjectByToken(token);
        List<Permission> permissionDTOs = permissionApiFacadeService.getAllPermissionByUserId(subject.getUserId());
        for (Permission permissionDTO : permissionDTOs) {
            if (resourceUri.equals(permissionDTO.getUri())) {
                return true;
            }
        }
        return false;
    }

    public List<Boolean> isPermitted(String token, List<String> resourceUris) {
        List<Boolean> isPermitteds = new ArrayList<Boolean>();
        for (String uri : resourceUris) {
            isPermitteds.add(isPermitted(token, uri));
        }
        return isPermitteds;
    }

    public Subject getSubjectByToken(String token) {
        Subject subject = (Subject) memcachedClient.get(token);
        if (null == subject) {
            throw new RuntimeException("该访问令牌无效或者过期！");
        }
        return subject;
    }

	 


}
