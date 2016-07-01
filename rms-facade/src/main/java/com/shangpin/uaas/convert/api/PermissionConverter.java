package com.shangpin.uaas.convert.api;
import com.shangpin.uaas.api.facade.auth.dto.PermissionDTO;
import com.shangpin.uaas.entity.Permission;

/**
 * 权限转换器
 */
/*@Category(Permission)*/
public class PermissionConverter {

    PermissionDTO convert (Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setRoleId(permission.getRoleId());
        permissionDTO.setResourceId(permission.getResourceId());
        permissionDTO.setUri(permission.getUri());
        return permissionDTO;
    }
}
