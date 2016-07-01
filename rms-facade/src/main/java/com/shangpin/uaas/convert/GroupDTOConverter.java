package com.shangpin.uaas.convert;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.shangpin.uaas.api.admin.org.GroupDTO;
import com.shangpin.uaas.entity.Group;

/**
 * Created by Administrator on 2014/8/18.
 */
/*@Category(GroupDTO)*/
public class GroupDTOConverter {

	public static  Group convert (GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());
        if (StringUtils.isNotEmpty(groupDTO.getDescription())) {
            group.setDescription(groupDTO.getDescription());
        }
        if (StringUtils.isNotEmpty(groupDTO.getDescription())) {
            group.setStatus(groupDTO.getIsEnabled());
        }
        group.setId(UUID.randomUUID().toString());
        group.setCreatedTime(new Date());
        group.setUpdatedTime( new Date());
        return group;
    }

	public static Group convert (Group group,GroupDTO groupDTO) {
        if (StringUtils.isNotEmpty(groupDTO.getName())) {
        	group.setName(groupDTO.getName());
        }
        if (StringUtils.isNotEmpty(groupDTO.getDescription())) {
            group.setDescription(groupDTO.getDescription());
        } else {
        	 group.setDescription("");
        }
        group.setStatus(groupDTO.getIsEnabled());
        return group;
    }

}
