package com.shangpin.uaas.services.dao;

import java.util.List;

import com.shangpin.uaas.entity.example.UserGroupExample;
import com.shangpin.uaas.services.dao.mapper.UserGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shangpin.uaas.entity.RoleGroup;
import com.shangpin.uaas.entity.UserGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @date:     2016年6月11日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
//@Repository
//@Transactional
@Service
public class UserGroupRepoService  {
	@Autowired
	private UserGroupMapper userGroupMapper;



//	@Query("select 1 from UserGroup u where groupId=?2 and exists userId=?1")
//	public boolean groupHasUser(String userId, String groupId) {return false;}

//	public void createUserGroup(UserGroup userGroup){}
	
//	@Query("from UserGroup u where groupId=?2 and userId=?1")
//	public UserGroup getUserOfGroup(String userId, String groupId){return null;}

	public int delete(String userGroupId){
		return userGroupMapper.deleteByPrimaryKey(userGroupId);
	}

	public int insert(UserGroup userGroup){
		return userGroupMapper.insert(userGroup);
	}

//	@Query("from UserGroup u where groupId=?1")
	public List<UserGroup> findUserGroupByGroupId(String groupId) {
		UserGroupExample example = new UserGroupExample();
		UserGroupExample.Criteria criteria = example.createCriteria();
		criteria.andGroupidEqualTo(groupId);
		return userGroupMapper.selectByExample(example);
	}

	/**
	 * 根据用户id查询用户的用户组
	 * @param userId 用户id
	 * @return
     */
	public List<UserGroup> findUserGroupByUserId(String userId) {
		UserGroupExample example = new UserGroupExample();
		UserGroupExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userId);
		return userGroupMapper.selectByExample(example);
	}

//	@Query("from UserGroup u where groupId=?1 and status=1")
//	public List<UserGroup> findEnabledUserGroupsByGroup(String groupId) {return null;}
	public List<UserGroup> findByUserIdGroupId(String userId,String groupId) {
		UserGroupExample example = new UserGroupExample();
		UserGroupExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userId);
		criteria.andGroupidEqualTo(groupId);
		return userGroupMapper.selectByExample(example);
	}
}
