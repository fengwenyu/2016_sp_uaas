package com.shangpin.uaas.convert.api;

import com.shangpin.uaas.api.facade.auth.dto.GroupDTO;
import com.shangpin.uaas.entity.Group;

/**
 * Created by Administrator on 2014/8/18.
 */
public class GroupConverter {

   public static GroupDTO convert(Group grp) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(grp.getId());
        groupDTO.setName(grp.getName());
        groupDTO.setDescription(grp.getDescription());
        groupDTO.setStatus(String.valueOf(grp.isStatus()));
        return groupDTO;
    }

}
