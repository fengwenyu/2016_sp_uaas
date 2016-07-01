package com.shangpin.uaas.convert;

import com.shangpin.uaas.api.admin.org.OrganizationDTO;
import com.shangpin.uaas.api.admin.user.UserDTO;
import com.shangpin.uaas.entity.Organization;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
/*@Category(Organization)*/
public class OrganizationConverter {

    public static OrganizationDTO toOrganizationDTO(Organization organization) {
    	OrganizationDTO result = new OrganizationDTO();
        result.setName(organization.getName());
        result.setId(organization.getId());
        result.setIsEnabled(organization.isStatus());
        result.setDescription(organization.getDescription());
        result.setParentId(organization.getParentId());

       /* String tempRdn = organization.getId().getSuffix(1).toString();
        String[] allRdns = tempRdn.split(",");
        for (int i = allRdns.length - 1; i >= 0; i--) {
            String one = allRdns[i];
            if (StringUtils.isNotEmpty(one) && one.split("=").length > 0) {
                rdn = rdn + one.split("=")[1] + "/";
            }
        }
        if (rdn.length() > 0) {
            result.setRdn(rdn.substring(0, rdn.length() - 1));
        } else {

        }*/
        result.setRdn(organization.getName());

        result.setCode(organization.getCode());
        if(StringUtils.isNotBlank(organization.getLeaderIds())){
            String[] leaderIds=organization.getLeaderIds().split(",");
            for (String leader : leaderIds) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(leader);
                result.getOrganizationLeaders().add(userDTO);
            }
        }
        return result;
    }

}
