package com.shangpin.uaas.convert;

import com.shangpin.uaas.api.admin.resource.ResourceDTO;
import com.shangpin.uaas.api.admin.resource.ResourceNodeDTO;
import com.shangpin.uaas.entity.Resource;


/**
 * 资源转换器
 *
 */
class ResourceConverter {

    ResourceDTO toCreateResourceDTO(Resource resource) {
    	ResourceNodeDTO result = new ResourceNodeDTO();
    	
//        result.id = uuid
//        result.description = description
//        result.enabled = enabled
//        result.functionId = functionId
//        result.moduleName = moduleName
//        result.level = level
//        result.uri = uri
//        result.parentResourceId = parentResourceId
//        result.type = type
    	return null;
    }

}
