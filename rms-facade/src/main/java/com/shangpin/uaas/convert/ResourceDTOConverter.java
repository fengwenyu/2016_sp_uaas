package com.shangpin.uaas.convert;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.shangpin.uaas.api.admin.resource.ResourceNodeDTO;
import com.shangpin.uaas.entity.Resource;
import com.shangpin.uaas.entity.ResourceNode;

/**
 * 资源DTO转换器
 */
/*@Category(ResourceNodeDTO)*/
public class ResourceDTOConverter {

   public static Resource toCreateResource(ResourceNodeDTO resourceNodeDTO) {
    	Resource result = new Resource();
        result.setId(UUID.randomUUID().toString());
        if (StringUtils.isNotEmpty(resourceNodeDTO.getUri())) {
        	result.setUri(resourceNodeDTO.getUri());
        }
        if (null != resourceNodeDTO.getIsEnabled()) {
            result.setEnabled(resourceNodeDTO.getIsEnabled());
        } else {
        	 result.setEnabled(false);
        }
        return result;
    }

   public static Resource toUpdateResource(ResourceNodeDTO resourceNodeDTO) {
    	Resource result = new Resource();
        result.setId(resourceNodeDTO.getId());
        if (StringUtils.isNotEmpty(resourceNodeDTO.getUri())) {
            result.setUri(resourceNodeDTO.getUri());
        }
        if (null == resourceNodeDTO.getIsEnabled()) {
            result.setFunctionId(String.valueOf(resourceNodeDTO.getIsEnabled()));
        }

        return result;
    }

   public static ResourceNode toCreateResourceNode(ResourceNodeDTO resourceNodeDTO) {
    	ResourceNode result = new ResourceNode();
        if (null != resourceNodeDTO.getType()) {
            result.setType(resourceNodeDTO.getType());
        }
        if (StringUtils.isNotEmpty(resourceNodeDTO.getName())) {
            result.setModuleName(resourceNodeDTO.getName());
        }
        if (StringUtils.isNotEmpty(resourceNodeDTO.getDescription())) {
            result.setDescription(resourceNodeDTO.getDescription());
        }

        if (StringUtils.isEmpty(resourceNodeDTO.getParentId()))
            result.setParentResourceId("1");
        else{
            result.setParentResourceId(resourceNodeDTO.getParentId());
        }
        return result;
    }

   public static ResourceNode toUpdateResourceNode(ResourceNodeDTO resourceNodeDTO) {
    	ResourceNode result = new ResourceNode();
        result.setId(resourceNodeDTO.getId());

        if (StringUtils.isNotEmpty(resourceNodeDTO.getDescription())) {
            result.setDescription(resourceNodeDTO.getDescription());
        }else{
            result.setDescription(null);
        }
        if (StringUtils.isNotEmpty(resourceNodeDTO.getType().toString())) {
            result.setType(resourceNodeDTO.getType());
        }
        if (StringUtils.isEmpty(resourceNodeDTO.getParentId()))
            result.setParentResourceId(Resource.PARENT);
        else
            result.setParentResourceId(resourceNodeDTO.getParentId());

        return result;
    }
}
