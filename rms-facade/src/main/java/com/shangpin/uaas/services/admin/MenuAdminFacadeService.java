package com.shangpin.uaas.services.admin;

import java.rmi.AlreadyBoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangpin.uaas.api.admin.menu.MenuAdminFacade;
import com.shangpin.uaas.api.admin.menu.MenuDTO;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;
import com.shangpin.uaas.api.facade.e.APPCode;
import com.shangpin.uaas.convert.MenuConverter;
import com.shangpin.uaas.convert.MenuDTOConverter;
import com.shangpin.uaas.entity.Menu;
import com.shangpin.uaas.services.api.AuthorizationFacadeService;
import com.shangpin.uaas.services.dao.MenuRepoService;
import com.shangpin.uaas.util.PageListUtil;

/**
 *
 */
@Service
public class MenuAdminFacadeService implements MenuAdminFacade {
	protected static Logger log = LoggerFactory.getLogger(MenuAdminFacadeService.class);
    @Autowired
    MenuRepoService menuRepoService;
    @Autowired
    FlushCacheService flushCacheService;

    final String BASE_DN = "ou=Menu";
    @Autowired
    AuthorizationFacadeService authorizationFacadeService; 

    @Override
    public String createMenu(MenuDTO menuDTO) {

    	Menu entity = MenuDTOConverter.toCreateEntity(menuDTO);
    	String menuId = menuDTO.getId();
        if(StringUtils.isNotEmpty(menuId)){
            if("1".equals(menuId)){
                try {
					throw new AlreadyBoundException("菜单索引不能为1");
				} catch (AlreadyBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            Menu menu = menuRepoService.findById(menuId);
            if(menu !=null){
                try {
					throw new AlreadyBoundException("该菜单索引已经存在");
				} catch (AlreadyBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }else{
                entity.setId(menuId);
            }
        }

        String parentId = entity.getParentId();
        if (StringUtils.isNotEmpty(parentId) &&
                !"1".equals(parentId) && !"0".equals(entity.getParentId())) {
            Menu parentMenu = menuRepoService.findById(parentId);
            if (parentMenu == null) {
            	throw new RuntimeException("该父级菜单项不存在：" + parentId);
            }
            entity.setAppCode(parentMenu.getAppCode());
            //Menu menu = parentMenus.get(0);
        } else {
            entity.setParentId("1");
            if(entity.getAppCode()==null){
                throw new  RuntimeException("顶级菜单的系统编码属性不能为空");
            }
        }
        int insert = menuRepoService.insert(entity);
        if(insert!=1){
            throw new  RuntimeException("创建菜单失败，菜单id："+entity.getId());
        }
        return entity.getId();
    }

    @Override
    public void modifyMenu(MenuDTO menuDTO) {
        flushCacheService.modify++;

        menuDTO.setParentId("0".equals(menuDTO.getParentId()) ? "1" : menuDTO.getParentId());
        String id = menuDTO.getId();
        Menu menu = menuRepoService.findById(id);
        if (menu == null) {
            throw new RuntimeException("没有该菜单项：" + id);
        }
        menu= MenuDTOConverter.toUpdateEntity(menu, menuDTO);
        if(!"1".equals(menu.getParentId())){
            Menu parentMenu = menuRepoService.findById(menu.getParentId());
            if (parentMenu == null) {
                throw new RuntimeException("该父级菜单项不存在：" + menu.getParentId());
            }
            menu.setAppCode(parentMenu.getAppCode());
        }
        menuRepoService.update(menu);
       // moveMenu(menuDTO.getId() , menuDTO.getParentId());
    }

    @Override
    public void moveMenu(String menuId, String targetMenuId) {
        flushCacheService.modify++;

        Menu menu = menuRepoService.findById(menuId);
        if (menu ==null) {
            throw new RuntimeException("没有该菜单项：" + menuId);
        }
        if (StringUtils.isEmpty(targetMenuId) || "1".equals(targetMenuId)) {
            targetMenuId = "1";//顶级菜单
        } else {
            Menu targetMenus = menuRepoService.findById(targetMenuId);
            if (targetMenus == null) {
                throw new RuntimeException("没有该菜单项：" + targetMenuId);
            }
        }
        menu.setParentId(targetMenuId);
        menuRepoService.update(menu);
    }

    @Override
    public void deleteMenu(String menuId) {
        flushCacheService.modify++;
        Menu menu = menuRepoService.findById(menuId);
        if (menu == null) {
            throw new RuntimeException("没有该菜单项：" + menuId);
        }
        menuRepoService.delete(menuId);
    }

    @Override
    public PagedList<MenuDTO> findMenusByParentId(String parentId, Paginator paginator) {
        if (StringUtils.isEmpty(parentId)) {
            parentId = "1";
        }
        List<Menu> menus = menuRepoService.findByParentId(parentId);
        log.debug("menus.size" + menus.size());
        List<MenuDTO> result=new ArrayList<>(menus.size());
        for (Menu menu : menus) {
        	result.add(MenuConverter.toMenuDTO(menu));
		}
        log.debug("result.size" + result.size());
        return PageListUtil.convert(paginator, result);
    }

    @Override
    public MenuDTO getMenu(String id) {
        Menu menu = menuRepoService.findById(id);
        if (menu == null) {
            throw new RuntimeException("没有该菜单项：" + id);
        }
        return MenuConverter.toMenuDTO(menu);
    }

    @Override
    public List<MenuDTO> getAllMenus() {
        
        List<Menu> menus = menuRepoService.findAll();
//        Collections.sort(menus)
        log.debug("Size:" + menus.size());
        List<MenuDTO> menuDTOs=new ArrayList<>();
        for (Menu m : menus) {
        	menuDTOs.add(MenuConverter.toMenuDTO(m));
		}
        return menuDTOs;
    }

    public List<MenuDTO> getAllMenusByAppCode(String appCode) {
        List<Menu> menus = menuRepoService.findByAppCodeAndParentId(appCode, "1");
        List<MenuDTO> menuDTOs=new ArrayList<>();
        if (menus.size() == 1) {
            Menu menu = menus.get(0);
            //TODO get all submenu use while
            List<Menu> menus1 = menuRepoService.findByParentId(menu.getId());
            for (Menu m : menus1) {
            	menuDTOs.add(MenuConverter.toMenuDTO(m));
			}
        }
        return menuDTOs;
    }

}
