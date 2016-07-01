package com.shangpin.uaas.convert.api;

import com.shangpin.uaas.api.facade.auth.dto.OrganizationDTO;
import com.shangpin.uaas.entity.Organization;

/**
 * Created by Percy on 14-7-23.
 */
/*@Category(Organization)*/
public class OrganizationConverter {

	public static OrganizationDTO convert(Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setCode(organization.getCode());
        organizationDTO.setId(organization.getId());
        organizationDTO.setName(organization.getName());
        organizationDTO.setParentId(organization.getParentId());
        return organizationDTO;
    }


}
