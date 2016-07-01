package com.shangpin.uaas.convert;

import com.shangpin.uaas.api.admin.permission.PermissionDTO;
import com.shangpin.uaas.entity.Permission;

/**
 *
 */
/*@Category(Permission)*/
public class PermissionConverter {

    public static PermissionDTO toPermissionDTO(Permission permission) {
    	PermissionDTO result = new PermissionDTO();
        result.setId(permission.getId());
        result.setResourceId(permission.getResourceId());
       //???result.setIsEnabled(String.valueOf(permission.getStatus()));
        result.setRoleId(permission.getRoleId());
        result.setDescription(permission.getDescription());
        result.setUri(permission.getUri());
        return result;
    }
}
