package com.shangpin.uaas.convert;

import org.apache.commons.lang3.StringUtils;

import com.shangpin.uaas.api.admin.resource.ResourceNodeDTO;
import com.shangpin.uaas.entity.Resource;
import com.shangpin.uaas.entity.ResourceNode;

/**
 * 资源组转换器
 *
 */
//@Category(ResourceNodeDTO)
public class ResourceNodeDTOConverter {

	public static ResourceNode toResourceNode(ResourceNode resourceNode,ResourceNodeDTO resourceNodeDTO) {

        if (StringUtils.isNotEmpty(resourceNodeDTO.getDescription())) {
            resourceNode.setDescription(resourceNodeDTO.getDescription());
        } else {
            resourceNode.setDescription(null);
        }
        if (null != resourceNodeDTO.getType()) {
            resourceNode.setType(resourceNodeDTO.getType());
        }
        if (StringUtils.isNotEmpty(resourceNodeDTO.getParentId()) && !"0".equals(resourceNodeDTO.getParentId())) {
            if (!resourceNode.getId().equals(resourceNodeDTO.getParentId())) {
                resourceNode.setParentResourceId(resourceNodeDTO.getParentId());
            }
        } else {
            resourceNode.setParentResourceId( "1");
        }
        if (StringUtils.isNotEmpty(resourceNodeDTO.getName())) {
            resourceNode.setModuleName(resourceNodeDTO.getName());
        }

        return resourceNode;
    }

	public static Resource toResource(Resource resource,ResourceNodeDTO resourceNodeDTO) {

        if (null != resourceNodeDTO.getIsEnabled()) {
            resource.setEnabled(resourceNodeDTO.getIsEnabled());
        }
//        if (StringUtils.isNotEmpty(uri)) {
//            resource.uri = uri
//        }

        return resource;
    }

}
