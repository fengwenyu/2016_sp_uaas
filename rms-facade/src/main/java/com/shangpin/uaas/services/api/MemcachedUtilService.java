package com.shangpin.uaas.services.api;

import com.shangpin.uaas.entity.Resource;
import com.shangpin.uaas.entity.Role;
import com.shangpin.uaas.services.dao.ResourceRepoService;
import com.shangpin.uaas.services.dao.RoleRepoService;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * memcached的缓存工具类（主要用来存放许可，认证）
 */
@Service
public class MemcachedUtilService {
    private final String resourceKey = "uaas:role:resource:uri";

    @Autowired
    MemcachedClient memcachedClient;

    @Autowired
    RoleRepoService roleRepoService;

    @Autowired
    ResourceRepoService resourceRepoService;

    /**
     * 设置所有角色对应的uri
     * @return
     */
    public boolean storAllRoleToCache(){
        try {
            //查出所有的role
            List<Role> allRoles = roleRepoService.findAll();
            Map<String,List<String>> roleMap = new HashMap<>();
            for (Role role : allRoles) {
                List<String> roleUri = new ArrayList<>();
                String roleId = role.getId();
                //根据roleId查询出所有的resourceUri
                List<Resource> resources = resourceRepoService.findResourcesByRoleId(roleId);
                for (Resource resource : resources) {
                    roleUri.add(resource.getUri()/*.replace("mvc-action://", "/")*/);
                }
                roleMap.put(roleId,roleUri);
            }
            memcachedClient.set(resourceKey,60*60*12,roleMap);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 更新对应角色的uri
     * @param roleId
     * @param type 1:add or update，2.delete
     * @return
     */
    public boolean updateRoleToCacheByRoleId(String roleId,String type){//type: 1:add or update，2.delete
        try {
            if(type==null){
                    throw new Exception("请求更新role缓存不合法，type未赋值");
            }
            //查出所有的role
            Map<String,List<String>> roleMap =(Map<String,List<String>>) memcachedClient.get(resourceKey);
            if(roleMap.isEmpty()){
                boolean b = storAllRoleToCache();
                if(!b){
                    throw new Exception("缓存无role数据，并更新失败");
                }
                roleMap =(Map<String,List<String>>) memcachedClient.get(resourceKey);
            }
            if("1".equals(type)){
                List<String> roleUri = new ArrayList<>();
                //根据roleId查询出所有的resourceUri
                List<Resource> resources = resourceRepoService.findResourcesByRoleId(roleId);
                for (Resource resource : resources) {
                    roleUri.add(resource.getUri()/*.replace("mvc-action://", "/")*/);
                }
                roleMap.put(roleId,roleUri);
            }else if("2".equals(type)){
                roleMap.remove(roleId);
            }
            memcachedClient.set(resourceKey,60*60*12,roleMap);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取所有的角色对应的uri
     * @return
     */
    public  Map<String,List<String>> getAllRoleFromCache(){
        Map<String,List<String>> roleMap =(Map<String,List<String>>) memcachedClient.get(resourceKey);
        if(roleMap==null){
            storAllRoleToCache();
            roleMap =(Map<String,List<String>>) memcachedClient.get(resourceKey);
        }
        return roleMap;
    }
}
