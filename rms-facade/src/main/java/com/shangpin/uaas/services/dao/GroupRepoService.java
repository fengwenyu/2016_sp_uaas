package com.shangpin.uaas.services.dao;

import java.util.List;

import com.shangpin.uaas.entity.example.GroupExample;
import com.shangpin.uaas.entity.example.RoleGroupExample;
import com.shangpin.uaas.services.dao.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shangpin.uaas.entity.Group;
import com.shangpin.uaas.entity.RoleGroup;
import com.shangpin.uaas.entity.UserGroup;

/**
 *
 * @date:     2016年6月17日 <br/>
 * @author 陈小峰
 * @since JDK7
 */
//@Repository
@Transactional
@Service
public class GroupRepoService {
	@Autowired
	private GroupMapper groupMapper;

	public int insert(Group group){
		return groupMapper.insert(group);
	}

	public int update(Group group){
		return groupMapper.updateByPrimaryKey(group);
	}

	//@Query("update #{#entityName} set status=?2 where uuid=?1")
	//public void enabledGroup(Group group, boolean isEnabled){}


	public List<Group> findGroupByName(String name){
		GroupExample example = new GroupExample();
		GroupExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name);
		return groupMapper.selectByExample(example);
	}

	public Group findById(String id){
		return groupMapper.selectByPrimaryKey(id);
	}


	//@Query("select 1 from UserGroup u where groupId=?2 and exists userId=?1")
//public boolean groupHasUser(String userId, String groupId){return false;}

	//@Query("from UserGroup u where groupId=?2 and userId=?1")
//	public UserGroup getUserOfGroup(String userId, String groupId){return null;}

	public int delete(String groupId){
		return groupMapper.deleteByPrimaryKey(groupId);
	}

//	public void delete(RoleGroup roleGroup){}

//	@Query("from RoleGroup u where groupId=?2 and roleId=?1")
	//public List<RoleGroup> hasRoleOfGroup(String roleId, String groupId){return null;}

//	@Query("from UserGroup u where groupId=?1")
//	public List<UserGroup> findUserGroupByGroupId(String groupId){return null;}

//	@Query("from RoleGroup u where roleId=?1")
	public List<Group> findUser_UserGroup_GroupByUserId(String userId){
		return groupMapper.findUser_UserGroup_GroupByUserId(userId);
	}

//	@Query("from RoleGroup u where groupId=?1")
//	public List<RoleGroup> findRoleGroupByGroupId(String groupId){return null;}

//	@Query("from UserGroup u where groupId=?1 and status=1")
	/*public List<UserGroup> findByUserId(String userId){
		GroupExample example = new GroupExample();
		GroupExample.Criteria criteria = example.createCriteria();
		criteria.andu
		return groupMapper.selectByExample(example);
	}*/

//	@Query("from RoleGroup u where groupId=?1 and status=1")
	//public List<RoleGroup> findEnabledRoleGroupsByGroup(String groupId){return null;}

//	@Query("from UserGroup u where groupId=?1 and status=1")
//	public List<UserGroup> findEnabledUserGroupsByGroup(String groupId){return null;}
	public List<Group> findAll(){
		GroupExample example = new GroupExample();
		return groupMapper.selectByExample(example);
	}
}
