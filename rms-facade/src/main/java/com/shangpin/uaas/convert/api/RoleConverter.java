package com.shangpin.uaas.convert.api;

import com.shangpin.uaas.api.facade.auth.dto.RoleDTO;
import com.shangpin.uaas.entity.Role;

/**
 * Created by Percy on 14-7-23.
 */
/*@Category(Role)*/
public class RoleConverter {

    public static RoleDTO convert(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setCode(role.getName());
        roleDTO.setDescription(role.getDescription());
        return roleDTO;
    }

}
