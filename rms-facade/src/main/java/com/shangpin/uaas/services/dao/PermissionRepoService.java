package com.shangpin.uaas.services.dao;

import java.util.List;

import com.shangpin.uaas.entity.example.PermissionExample;
import com.shangpin.uaas.services.dao.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shangpin.uaas.entity.Permission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
//@Repository
//@Transactional
@Service
public class PermissionRepoService {
    @Autowired
    private PermissionMapper permissionMapper;

    public List<Permission> findByResourceId(String resoureId) {
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        criteria.andResourceidEqualTo(resoureId);
        return permissionMapper.selectByExample(example);
    }


    /**
     * 根据角色id查询许可权限
     * @param roleId
     * @return
     */
    public List<Permission> findByRoleId(String roleId) {
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidEqualTo(roleId);
        return permissionMapper.selectByExample(example);
    }

    public List<Permission> findByUserId(String userId) {
        return permissionMapper.findByUserId(userId);
    }

    public List<Permission> findByRoleIdIn(List<String> roleIds) {
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidIn(roleIds);
        return permissionMapper.selectByExample(example);
    }

    /**
     * 根据资源标识以及角色标识查询资源
     *
     * 该方法一般用作防止重复注册权限，防止脏数据
     *
     * @param resourceId
     * @param roleId
     * @return
     */
    public List<Permission> findByResourceIdAndRoleId(String resourceId, String roleId){
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        criteria.andResourceidEqualTo(resourceId);
        criteria.andRoleidEqualTo(roleId);
        return permissionMapper.selectByExample(example);
    }
    
   //public List<Permission> findByRoleIdIn(List<String> roleIds){return null;}
  //  public void deleteByRoleId(String roleId){}
    public int delete(String id){
        return permissionMapper.deleteByPrimaryKey(id);
    }
    public int deleteByRoleId(String roleId){
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidEqualTo(roleId);
        return permissionMapper.deleteByExample(example);
    }
    public int insert(Permission permission){
        return  permissionMapper.insert(permission);
    }
    public int update(Permission permission){
        return  permissionMapper.updateByPrimaryKey(permission);
    }
    public int deleteByResourceId(String resourceId){
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        criteria.andResourceidEqualTo(resourceId);
        return permissionMapper.deleteByExample(example);
    }
}
