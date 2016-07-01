package com.shangpin.uaas.convert;

import com.shangpin.uaas.api.admin.menu.MenuDTO;
import com.shangpin.uaas.api.admin.menu.MenuNodeDTO;
import com.shangpin.uaas.entity.Menu;

/**
 *
 */
/*@Category(Menu)*/
public class MenuConverter {

	public static  MenuDTO toMenuDTO(Menu menu) {
    	MenuDTO result = new MenuDTO();
    	result.setId(menu.getId());
        result.setName(menu.getName());
        result.setUri(menu.getUri());
        result.setParentId(menu.getParentId());
        result.setAppCode(menu.getAppCode());
        result.setUrl(menu.getUrl());
        result.setSort( menu.getSort());
        result.setHasParent(!"1".equals(menu.getParentId()));
        return result;
    }

	public static MenuNodeDTO toMenuNodeDTO(Menu menu) {
        MenuNodeDTO menuNodeDTO = new MenuNodeDTO();
        menuNodeDTO.setName(menu.getName());
        menuNodeDTO.setId(menu.getId());
        menuNodeDTO.setAppCode(menu.getAppCode());
        menuNodeDTO.setSort(menu.getSort());
        menuNodeDTO.setUrl(menu.getUrl());
        return menuNodeDTO;
    }

}
