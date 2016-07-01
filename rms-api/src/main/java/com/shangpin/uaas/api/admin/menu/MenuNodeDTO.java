package com.shangpin.uaas.api.admin.menu;

import java.util.List;

/**
 * 菜单组
 */
public class MenuNodeDTO extends MenuDTO {
    private List<MenuNodeDTO> menuDTOs;

    public List<MenuNodeDTO> getMenuDTOs() {
        return menuDTOs;
    }

    public void setMenuDTOs(List<MenuNodeDTO> menuDTOs) {
        this.menuDTOs = menuDTOs;
    }
}
