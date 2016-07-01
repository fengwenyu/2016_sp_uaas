package com.shangpin.uaas.convert.api;

import com.shangpin.uaas.api.facade.user.UserRoleDTO;
import com.shangpin.uaas.entity.UserRole;

/**
 * 用户角色转换器
 */
 /*@Category(UserRole)*/
public class UserRoleConverter {

  public UserRoleDTO convert (UserRole userRole) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(userRole.getUserId());
        userRoleDTO.setRoleId(userRole.getRoleId());
        return  userRoleDTO;
    }

}
