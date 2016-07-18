package com.shangpin.uaas.services.admin;

import com.shangpin.uaas.api.admin.menu.MenuAdminFacade;
import com.shangpin.uaas.api.admin.menu.MenuDTO;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;
import com.shangpin.uaas.convert.MenuConverter;
import com.shangpin.uaas.convert.MenuDTOConverter;
import com.shangpin.uaas.entity.Menu;
import com.shangpin.uaas.services.api.AuthorizationFacadeService;
import com.shangpin.uaas.services.dao.MenuRepoService;
import com.shangpin.uaas.util.PageListUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

//    final String BASE_DN = "ou=Menu";
    @Autowired
    AuthorizationFacadeService authorizationFacadeService; 

    @Override
    public String createMenu(MenuDTO menuDTO) throws Exception{

    	Menu entity = MenuDTOConverter.toCreateEntity(menuDTO);
    	String menuId = menuDTO.getId();
        if(StringUtils.isNotEmpty(menuId)){
            if("1".equals(menuId)){
                return "菜单编码不能为1";
                //throw new AlreadyBoundException("菜单索引不能为1");
            }
            Menu menu = menuRepoService.findById(menuId);
            if(menu !=null){
                return "该菜单编码已经存在";
               // throw new AlreadyBoundException("该菜单索引已经存在");
            }else{
                entity.setId(menuId);
            }
        }

        String parentId = entity.getParentId();
        if (StringUtils.isNotEmpty(parentId) && !"1".equals(parentId) && !"0".equals(entity.getParentId())) {
            Menu parentMenu = menuRepoService.findById(parentId);
            if (parentMenu == null) {
                return "该父级菜单项不存在";
//            	throw new RuntimeException("该父级菜单项不存在：" + parentId);
            }
            entity.setAppCode(parentMenu.getAppCode());
            //Menu menu = parentMenus.get(0);
        } else {
            entity.setParentId("1");
            if(entity.getAppCode()==null){
                return "顶级菜单的系统编码属性不能为空";
//                throw new  RuntimeException("顶级菜单的系统编码属性不能为空");
            }
        }
        List<Menu> byMenuNameAndParentId = menuRepoService.findByMenuNameAndParentId(entity.getName(), entity.getParentId());
        if(byMenuNameAndParentId!=null &&byMenuNameAndParentId.size()>0){
            return "该位置已经存在了相同名称的菜单项！";
        }
        //uri判断逻辑，由于后台数据问题，暂时屏蔽
       /* //校验uri是否正确
        String menuUri = "mvc-action://"+entity.getAppCode().toLowerCase()+"/";
        if(!entity.getUri().startsWith(menuUri)){
            return "URI开头应该为"+menuUri;
        }*/
        //判断uri不重复
        List<Menu> byUri = menuRepoService.findByUri(entity.getUri());
        if(byUri!=null &&byUri.size()>0){
           return "该URI 已被"+byUri.get(0).getName()+"使用！";
        }

        int insert = menuRepoService.insert(entity);
        if(insert!=1){
            throw new  RuntimeException("创建菜单失败，菜单id："+entity.getId());
        }
        return "success";
    }

    @Override
    public String modifyMenu(MenuDTO menuDTO) {
        flushCacheService.modify++;

        menuDTO.setParentId("0".equals(menuDTO.getParentId()) ? "1" : menuDTO.getParentId());
        String id = menuDTO.getId();
        Menu menu = menuRepoService.findById(id);
        if (menu == null) {
            return "当前菜单不存在！";
//            throw new RuntimeException("没有该菜单项：" + id);
        }

        menu= MenuDTOConverter.toUpdateEntity(menu, menuDTO);
        if(!"1".equals(menu.getParentId())){
            Menu parentMenu = menuRepoService.findById(menu.getParentId());
            if (parentMenu == null) {
                return menu.getName()+"的父菜单不存在";
//                throw new RuntimeException("该父级菜单项不存在：" + menu.getParentId());
            }
            menu.setAppCode(parentMenu.getAppCode());
        }
        //判断相同父元素下没有相同的名字
        List<Menu> byMenuNameAndParentId = menuRepoService.findByMenuNameAndParentId(menu.getName(), menu.getParentId());
        if(byMenuNameAndParentId!=null &&byMenuNameAndParentId.size()>0){
            for (Menu menu1 : byMenuNameAndParentId) {
                if (!menu1.getId().equals(menu.getId())){
                    return "该位置已经存在了相同名称的菜单项！";
                }
            }
        }
        //uri判断逻辑，由于后台数据问题，暂时屏蔽
        /*//校验uri是否正确
        String menuUri = "mvc-action://"+menu.getAppCode().toLowerCase()+"/";
        if(!menu.getUri().startsWith(menuUri)){
            return "URI开头应该为"+menuUri;
        }*/
        //判断uri不重复
        List<Menu> byUri = menuRepoService.findByUri(menu.getUri());
        if(byUri!=null &&byUri.size()>0){
            for (Menu menu2 : byUri) {
                if (!menu2.getId().equals(menu.getId())){
                    return "该URI 已被"+menu2.getName()+"使用！";
                }
            }
        }

        int update = menuRepoService.update(menu);
        if(update!=1){
            throw new RuntimeException("更新菜单失败,菜单id："+menu.getId());
        }
        return "success";
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
        log.debug("Size:" + menus.size());
        List<MenuDTO> menuDTOs=new ArrayList<>();
        for (Menu m : menus) {
        	menuDTOs.add(MenuConverter.toMenuDTO(m));
		}
        return menuDTOs;
    }

    public List<MenuDTO> getAllMenusByAppCode(String appCode) {
        List<Menu> menus;
        if(StringUtils.isBlank(appCode)){
            menus= menuRepoService.findByParentId("1");
        }else{
            menus= menuRepoService.findByAppCodeAndParentId(appCode, "1");
        }
        return getAllChildMenuDtoByMenu(menus);
    }

    public List<MenuDTO> findMenusByMenuName(String menuName) {
        List<Menu> menus;
        if(StringUtils.isBlank(menuName)){
            menus= menuRepoService.findByParentId("1");
        }else{
            menus= menuRepoService.findByMenuName(menuName);
        }
        return getAllChildMenuDtoByMenu(menus);
    }

    /**
     * 递归获取菜单节点下的所有子节点
     * @param menu menu对象
     * @param menus 放置menu的集合
     */
    public List<Menu> getAllChildMenuList(Menu menu,List<Menu> menus){
        menus.add(menu);
        //获取所有的子节点
        List<Menu> menus1 = menuRepoService.findByParentId(menu.getId());
        Collections.sort(menus1, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o1.getSort()-o2.getSort();
            }
        });
        if(!menus1.isEmpty()){
            for (Menu menu1 : menus1) {
                getAllChildMenuList(menu1,menus);
            }
        }
        return menus;
    }


    public List<MenuDTO> getAllChildMenuDtoByMenu(List<Menu> menus){
        List<MenuDTO> menuDTOs=new ArrayList<>();
        if (menus!=null && !menus.isEmpty()) {
            for (Menu menu : menus) {
                List<Menu> menusResult = getAllChildMenuList(menu, new ArrayList<Menu>());
                for (Menu m : menusResult) {
                    menuDTOs.add(MenuConverter.toMenuDTO(m));
                }
            }
        }
        return menuDTOs;
    }

}
