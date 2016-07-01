package com.shangpin.uaas.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.shangpin.common.utils.DateUtil;
import com.shangpin.uaas.api.admin.role.RoleDTO;
import com.shangpin.uaas.api.admin.role.RoleType;
import com.shangpin.uaas.entity.Role;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * 角色转化器
 *
 */
public class RoleConverter {

	public static RoleDTO toRoleDTO(Role role) {
		RoleDTO result = new RoleDTO();
		result.setIsEnabled(role.isStatus());
		result.setCode(role.getName());
		result.setDescription(role.getDescription());
		result.setId(role.getId());
		result.setCreatedTime(DateUtil.date10(role.getCreatedTime()));
		/*String type;
		if (StringUtils.isBlank(role.getType())){
			type=null;
		}else{
			switch (role.getType()){
				case "1":
					type="普通";
					break;
				default:
					type=null;
					break;
			}
		}*/
		result.setType(role.getType());
		return result;
	}

	public static List<RoleDTO> toRoleDTOList(Collection<Role> roles) {
		List<RoleDTO> result = new ArrayList<>();
		for (Role role : roles) {
			result.add(RoleConverter.toRoleDTO(role));
		}
		return result;
	}

}
