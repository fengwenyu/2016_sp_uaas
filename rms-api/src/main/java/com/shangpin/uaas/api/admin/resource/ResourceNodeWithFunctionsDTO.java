package com.shangpin.uaas.api.admin.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qiang on 7/20/14.
 */
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceNodeWithFunctionsDTO extends ResourceNodeDTO {
    private List<FunctionDTO> functions = new ArrayList<FunctionDTO>();
    private boolean hasChildren;
    private List<ResourceNodeWithFunctionsDTO> children = new ArrayList<ResourceNodeWithFunctionsDTO>();

    public ResourceNodeWithFunctionsDTO(ResourceNodeDTO resourceNodeDTO, List<FunctionDTO> functions, boolean hasChildren) {
        this.setParentId(resourceNodeDTO.getParentId());
        this.setName(resourceNodeDTO.getName());
        this.setDescription(resourceNodeDTO.getDescription());
        this.setId(resourceNodeDTO.getId());
        this.setIsEnabled(resourceNodeDTO.getIsEnabled());
        this.setResourceId(resourceNodeDTO.getResourceId());
        this.setType(resourceNodeDTO.getType());
        this.setUri(resourceNodeDTO.getUri());
        this.setFunctions(functions);
        this.setHasChildren(hasChildren);
    }

    public List<ResourceNodeWithFunctionsDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceNodeWithFunctionsDTO> children) {
        this.children = children;
    }

    public List<FunctionDTO> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionDTO> functions) {
        this.functions = functions;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
