package com.shangpin.uaas.services.dao;

import java.util.List;

import com.shangpin.uaas.entity.example.RoleGroupExample;
import com.shangpin.uaas.services.dao.mapper.RoleGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shangpin.uaas.entity.RoleGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @date:     2016年6月11日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
//@Repository
@Transactional
	@Service
public class RoleGroupRepoService  {
	@Autowired
	private RoleGroupMapper roleGroupMapper;

	//public void delete(RoleGroup roleGroup){}
	public int update(RoleGroup roleGroup){
		return roleGroupMapper.updateByPrimaryKey(roleGroup);
	}

//	@Query("from RoleGroup u where groupId=?2 and roleId=?1")
	//public List<RoleGroup> hasRoleOfGroup(String roleId, String groupId){return null;}


//	@Query("from RoleGroup u where roleId=?1")
	public List<RoleGroup> findRoleGroupByRoleId(String roleId) {
		RoleGroupExample example = new RoleGroupExample();
		RoleGroupExample.Criteria criteria = example.createCriteria();
		criteria.andRoleidEqualTo(roleId);
		return roleGroupMapper.selectByExample(example);
	}


//	@Query("from RoleGroup u where groupId=?1 and status=1")
	public List<RoleGroup> findRoleGroupsByGroupId(String groupId){
		RoleGroupExample example = new RoleGroupExample();
		RoleGroupExample.Criteria criteria = example.createCriteria();
		criteria.andGroupidEqualTo(groupId);
		return roleGroupMapper.selectByExample(example);
	}
	public List<RoleGroup> findByRoleIdGroupId(String roleId,String groupId){
		RoleGroupExample example = new RoleGroupExample();
		RoleGroupExample.Criteria criteria = example.createCriteria();
		criteria.andGroupidEqualTo(groupId);
		criteria.andRoleidEqualTo(roleId);
		return roleGroupMapper.selectByExample(example);
	}

}
