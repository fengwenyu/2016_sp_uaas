package com.shangpin.uaas.api.admin.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Qiang on 7/20/14.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FunctionDTO extends ResourceNodeDTO {
    private boolean isGranted;

    public boolean isGranted() {
        return isGranted;
    }

    public void setGranted(boolean isGranted) {
        this.isGranted = isGranted;
    }

    public FunctionDTO(ResourceNodeDTO resourceNodeDTO, boolean isGranted) {
        this.setParentId(resourceNodeDTO.getParentId());
        this.setName(resourceNodeDTO.getName());
        this.setGranted(isGranted);
        this.setDescription(resourceNodeDTO.getDescription());
        this.setId(resourceNodeDTO.getId());
        this.setIsEnabled(resourceNodeDTO.getIsEnabled());
        this.setResourceId(resourceNodeDTO.getResourceId());
        this.setType(resourceNodeDTO.getType());
        this.setUri(resourceNodeDTO.getUri());
    }
}
