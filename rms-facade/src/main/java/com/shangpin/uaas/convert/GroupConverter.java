package com.shangpin.uaas.convert;

import com.shangpin.common.utils.DateUtil;
import com.shangpin.uaas.api.admin.org.GroupDTO;
import com.shangpin.uaas.entity.Group;

/**
 * Created by Administrator on 2014/8/18.
 */
 /*@Category(Group)*/
public class GroupConverter {

	public static GroupDTO convert(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setName(group.getName());
        groupDTO.setIsEnabled(group.isStatus());
        groupDTO.setDescription(group.getDescription());
        groupDTO.setCreatedTime(group.getCreatedTime()!=null?DateUtil.datetime19(group.getCreatedTime()):"");
        groupDTO.setId(group.getId());
        return groupDTO;
    }

}
