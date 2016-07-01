package com.shangpin.uaas.convert;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.shangpin.uaas.api.admin.org.OrganizationDTO;
import com.shangpin.uaas.api.admin.user.UserDTO;
import com.shangpin.uaas.entity.Organization;

/**
 *
 */
public class OrganizationDTOConverter {

	public static  Organization toCreateEntity(OrganizationDTO organizationDTO) {
    	Organization result = new Organization();
        if (StringUtils.isNotEmpty(organizationDTO.getDescription())) {
            result.setDescription(organizationDTO.getDescription());
        }
        if (StringUtils.isNotEmpty(organizationDTO.getName())) {
            result.setName(organizationDTO.getName());
        }
        if (StringUtils.isNotEmpty(organizationDTO.getCode())) {
            result.setCode(organizationDTO.getCode());
        }
        if (StringUtils.isNotEmpty(organizationDTO.getParentId()) && !"1".equals(organizationDTO.getParentId())) {
            result.setParentId(organizationDTO.getParentId());
        } else {
            result.setParentId("1");
        }

        result.setStatus(organizationDTO.getIsEnabled());

        result.setId(UUID.randomUUID().toString());
        StringBuffer sb = new StringBuffer();
        for (UserDTO user : organizationDTO.getOrganizationLeaders()) {
        	sb.append(user.getId()).append(",");
		}
        result.setLeaderIds(sb.toString());
        return result;
    }

	public static Organization toUpdateEntity(Organization organization,OrganizationDTO organizationDTO) {
        if (StringUtils.isNotEmpty(organizationDTO.getDescription())) {
            organization.setDescription(organizationDTO.getDescription());
        }
        StringBuffer sb = new StringBuffer();
        for (UserDTO user : organizationDTO.getOrganizationLeaders()) {
        	sb.append(user.getId()).append(",");
		}
        organization.setLeaderIds(sb.toString());
        return organization;
    }

}
