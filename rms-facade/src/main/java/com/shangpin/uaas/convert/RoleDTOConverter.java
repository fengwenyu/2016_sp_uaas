package com.shangpin.uaas.convert;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.shangpin.uaas.api.admin.role.RoleDTO;
import com.shangpin.uaas.api.admin.role.RoleType;
import com.shangpin.uaas.entity.Role;

/**
 *
 */
 //@Category(RoleDTO)
public class RoleDTOConverter {

    public static Role toCreateEntity(RoleDTO roleDTO) {
    	Role result = new Role();

        result.setCreatedTime(new Date());
        result.setId(UUID.randomUUID().toString());
        if (StringUtils.isNotEmpty(roleDTO.getDescription())) {
            result.setDescription(roleDTO.getDescription());
        }
        if (StringUtils.isNotEmpty(roleDTO.getCode())) {
            result.setName(roleDTO.getCode());
        }
        result.setStatus(roleDTO.getIsEnabled());
        result.setType(roleDTO.getType());
        return result;
    }

    public static Role toUpdateEntity(Role role,RoleDTO roleDTO) {
        if (StringUtils.isNotEmpty(roleDTO.getDescription())) {
            role.setDescription(roleDTO.getDescription());
        } else {
            role.setDescription("");
        }
        /*if (StringUtils.isNotEmpty(roleDTO.getId())) {
            role.setId(roleDTO.getId());
        }*/
        if(StringUtils.isNotBlank(roleDTO.getCode())){
            role.setName(roleDTO.getCode());
        }else{
            role.setName("");
        }
        role.setType(roleDTO.getType());
        role.setUpdatedTime(new Date());
        role.setStatus(roleDTO.getIsEnabled());
        return role;
    }

}
