package com.shangpin.uaas.services.dao;

import java.util.List;
import java.util.Set;

import com.shangpin.uaas.api.admin.user.Status;
import com.shangpin.uaas.api.common.Paginator;
import com.shangpin.uaas.entity.example.RoleExample;
import com.shangpin.uaas.services.dao.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shangpin.uaas.api.admin.role.RoleCriteria;
import com.shangpin.uaas.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色
 */
//@Repository
//@Transactional
	@Service
public class RoleRepoService {
	@Autowired
	private RoleMapper roleMapper;
	/**
     * 创建角色
     *
     * @param role
     */
    public int insert(Role role) {
		return roleMapper.insert(role);
	}

	/**
     * 更新角色
     *
     * @param role
     */
    public int update(Role role){
		return roleMapper.updateByPrimaryKey(role);
	}

	/**
     * 删除角色
     *
     * @param roleId
     */
    public int delete(String roleId) {
		return roleMapper.deleteByPrimaryKey(roleId);
	}

	/*
     * 列出所有角色
     *
     * @return
     */
  //  List<Role> listRoles()  {return null;}

	/**
     * 根据角色编号查找角色
     *
     * @param name
     * @return
     */
   public List<Role> findByName(String name) {
	   RoleExample example = new RoleExample();
	   RoleExample.Criteria criteria = example.createCriteria();
	   criteria.andNameEqualTo(name);
	   return roleMapper.selectByExample(example);
   }

	/**
     * 根据角色ID获取
     *
     * @param id
     * @return
     */
   public Role findById(String id) {
	   return roleMapper.selectByPrimaryKey(id);
   }

	//public List<Role> findAll(RoleCriteria spec) {return null;}
//	@Query("update #{#entityName} set status=?2 where roleId=?1")
//	public int enabledRole(String roleId, boolean b) {return 0;}

	// dn newdn
//	public void renameRole(String old, String desc){}

	public  List<Role> findAll() {
		return roleMapper.findAll();
	}
	public  List<Role> findByCriteriaDto(RoleCriteria roleCriteria, Paginator paginator) {
		int start =(int) paginator.getOffset();
		int size = paginator.getPageSize();
		Boolean isEnabled = roleCriteria.getIsEnabled();
		String status =null;
		if(isEnabled!=null){
			status =roleCriteria.getIsEnabled()?"1":"0";
		}
		return roleMapper.findByCriteriaDto(roleCriteria,status,start,size);
	}
	public  long findCountByCriteriaDto(RoleCriteria roleCriteria) {
		Boolean isEnabled = roleCriteria.getIsEnabled();
		String status =null;
		if(isEnabled!=null){
			status =roleCriteria.getIsEnabled()?"1":"0";
		}
		return roleMapper.findCountByCriteriaDto(roleCriteria,status);
	}
//	public  Role findOne(String roleId) {return null;}
//	public  Role getOne(String id) {return null;}
	public  List<Role> findAllIn(List<String> ids) {
		RoleExample example = new RoleExample();
		RoleExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		return roleMapper.selectByExample(example);
	}
	public  List<Role> findByUserId(String userId) {
		return roleMapper.findByUserId(userId);
	}
}
