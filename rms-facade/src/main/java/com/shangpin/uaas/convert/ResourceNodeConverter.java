package com.shangpin.uaas.convert;

import org.apache.commons.lang3.StringUtils;

import com.shangpin.uaas.api.admin.resource.ResourceNodeDTO;
import com.shangpin.uaas.entity.Resource;
import com.shangpin.uaas.entity.ResourceNode;


/**
 * 资源转换器
 */
public class ResourceNodeConverter {

    public static ResourceNodeDTO toResourceNodeDTO(ResourceNode resourceNode) {
        ResourceNodeDTO result = new ResourceNodeDTO();
        result.setType(resourceNode.getType());
        result.setDescription(resourceNode.getDescription());
        result.setParentId(resourceNode.getParentResourceId());
        result.setName(resourceNode.getModuleName());
        result.setId(resourceNode.getId());
        result.setResourceId(resourceNode.getResourceId());
        boolean isEnabled="1".equals(resourceNode.getEnabled());
        result.setIsEnabled(isEnabled);
        result.setUri(resourceNode.getUri());
        result.setHasParent(!"1".equals(resourceNode.getParentResourceId()));
        return result;
    }

    public static ResourceNode toUpdateResourceNode(ResourceNodeDTO resourceNode) {
        ResourceNode result = new ResourceNode();
        if (StringUtils.isNotEmpty(resourceNode.getName())) {
            result.setModuleName(resourceNode.getName());
        }
        if (StringUtils.isNotEmpty(resourceNode.getDescription())) {
            result.setDescription(resourceNode.getDescription());
        }
        if (null != resourceNode.getType()) {
            result.setType(resourceNode.getType());
        }
        if (StringUtils.isNotEmpty(resourceNode.getParentId())) {
            result.setParentResourceId(resourceNode.getParentId());
        }
        return result;
    }

    public static  Resource toUpdateResource(ResourceNodeDTO resourceNode) {
        Resource result = new Resource();
        return result;
    }
}
