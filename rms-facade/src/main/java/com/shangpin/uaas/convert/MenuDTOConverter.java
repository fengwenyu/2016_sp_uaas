package com.shangpin.uaas.convert;

import com.shangpin.uaas.api.admin.menu.MenuDTO;
import com.shangpin.uaas.entity.Menu;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 *
 */
/*@Category(MenuDTO)*/
public class MenuDTOConverter {

	public static Menu toCreateEntity(MenuDTO menuDTO) {
    	Menu result = new Menu();

        result.setId(UUID.randomUUID().toString());

        if (StringUtils.isNotEmpty(menuDTO.getUri())) {
            result.setUri(menuDTO.getUri());
        }
        if (StringUtils.isEmpty(menuDTO.getParentId())) {
            result.setParentId("1");
        } else {
            result.setParentId(menuDTO.getParentId());
        }
        if(menuDTO.getSort() > 0){
            result.setSort(menuDTO.getSort());
        }
        if (StringUtils.isNotEmpty(menuDTO.getName())) {
            result.setName(menuDTO.getName());
        }
        if (StringUtils.isNotEmpty(menuDTO.getAppCode())) {
            result.setAppCode(menuDTO.getAppCode());
        } else {
            result.setAppCode(null);
        }
        if (StringUtils.isNotEmpty(menuDTO.getUrl())) {
            result.setUrl(menuDTO.getUrl());
        } else {
            result.setUrl(null);
        }

        return result;
    }

	public static Menu toUpdateEntity(Menu ldapMenu,MenuDTO menuDTO) {
        if (StringUtils.isNotEmpty(menuDTO.getUri())) {
            ldapMenu.setUri(menuDTO.getUri());
        }
        if (StringUtils.isNotEmpty(menuDTO.getParentId())) {
            ldapMenu.setParentId(menuDTO.getParentId());
        }
        if (StringUtils.isNotEmpty(menuDTO.getName())) {
            ldapMenu.setName(menuDTO.getName());
        }
        if (StringUtils.isNotEmpty(menuDTO.getAppCode())) {
            ldapMenu.setAppCode(menuDTO.getAppCode());
        } else {
            ldapMenu.setAppCode(null);
        }
        if (StringUtils.isNotEmpty(menuDTO.getUrl())) {
            ldapMenu.setUrl(menuDTO.getUrl());
        } else {
            ldapMenu.setUrl(null);
        }
        ldapMenu.setSort(menuDTO.getSort());
        return ldapMenu;
    }

}
