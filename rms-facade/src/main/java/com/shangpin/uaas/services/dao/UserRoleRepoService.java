package com.shangpin.uaas.services.dao;

import java.util.List;

import com.shangpin.uaas.entity.example.UserRoleExample;
import com.shangpin.uaas.services.dao.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import com.shangpin.uaas.entity.UserRole;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户角色
 */
//@Repository
//@Transactional
    @Service
public class UserRoleRepoService /*extends Mapper<UserRole>*/{
    @Autowired
    UserRoleMapper userRoleMapper;
    /**
     * 绑定角色
     *
     * @param userRole
     */
   // @CacheEvict(value = "defaultCache" , key="'EnabledUserRoleByUserId:' + #userRole.userId")
    public int insert(UserRole userRole){
        return userRoleMapper.insert(userRole);
    }

    public List<UserRole> findByUserId(String userId) {
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        return userRoleMapper.selectByExample(example);
    }

//    @Query("from #{#entityName} where status=1 and userId=?1")
    public List<UserRole> findUserRoles(String userId) {
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        return userRoleMapper.selectByExample(example);
    }

    public int update(UserRole userRole) {
        return userRoleMapper.updateByPrimaryKey(userRole);
    }
    
   // public void deleteByUserIdAndRoleId(String userId,String roleId){}
    
    public List<UserRole> findByRoleId(String roleId) {
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidEqualTo(roleId);
        return userRoleMapper.selectByExample(example);
    }
    
    public List<UserRole> findByUserIdAndRoleId(String userId,String roleId){
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidEqualTo(roleId);
        criteria.andUseridEqualTo(userId);
        return userRoleMapper.selectByExample(example);
    }
    
//    @Query("from #{#entityName} where status=1 and roleId=?1")
  //  public List<UserRole> findEnabledUserByRole(String roleId){return null;}
    public int delete(String id){
        return userRoleMapper.deleteByPrimaryKey(id);
    }
    public int deleteByUserIdAndRoleId(String roleId, String userId){
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidEqualTo(roleId);
        criteria.andUseridEqualTo(userId);
        return userRoleMapper.deleteByExample(example);
    }

    
}
