package com.shangpin.uaas.convert.api;

import com.shangpin.uaas.api.admin.user.Status;
import com.shangpin.uaas.api.facade.auth.dto.OrganizationDTO;
import com.shangpin.uaas.api.facade.user.UserDTO;
import com.shangpin.uaas.entity.User;

/**
 * Created by Percy on 14-7-23.
 */
/*@Category(User)*/
public class UserConverter {

   public static UserDTO convert(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setStatus(user.isStatus()? Status.ENABLED:Status.DISABLED);
        userDTO.setDirectLeaderId(user.getDirectLeaderId());
        userDTO.setEmail(user.getEmail());
        userDTO.setGender(user.getGender().name());
        userDTO.setId(user.getId());
        userDTO.setJobLevel(user.getJobLevel());
        userDTO.setMobile(user.getMobile());
        userDTO.setRealName(user.getRealName());
        userDTO.setTelephone(user.getTelephone());
        userDTO.setUserCode(user.getUserCode());
        userDTO.setUsername(user.getUsername());
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(user.getOrganizationId());
        userDTO.setOrganizationDTO(organizationDTO);
        return userDTO;

    }


}
