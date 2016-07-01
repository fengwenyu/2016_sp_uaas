package com.shangpin.uaas.services.admin;

import com.shangpin.uaas.api.admin.org.GroupAdminFacade;
import com.shangpin.uaas.api.admin.org.GroupDTO;
import com.shangpin.uaas.api.admin.role.RoleDTO;
import com.shangpin.uaas.api.admin.user.UserCriteriaDTO;
import com.shangpin.uaas.api.admin.user.UserWithGroupDTO;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;
import com.shangpin.uaas.convert.GroupConverter;
import com.shangpin.uaas.convert.GroupDTOConverter;
import com.shangpin.uaas.convert.RoleConverter;
import com.shangpin.uaas.convert.UserConverter;
import com.shangpin.uaas.entity.*;
import com.shangpin.uaas.services.dao.*;
import com.shangpin.uaas.sort.GroupComparator;
import com.shangpin.uaas.util.PageListUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class GroupAdminFacadeService implements GroupAdminFacade {
	protected static Logger log = LoggerFactory.getLogger(GroupAdminFacadeService.class);
    final String BASE_DN = "ou=Group";
    final String USER_Group_DN = "ou=UserGroup";
    final String ROLE_Group_DN = "ou=RoleGroup";
    final String USER_DN = "ou=Department";
    @Autowired
    GroupRepoService groupRepoService;
    @Autowired
    RoleRepoService roleRepoService;
    @Autowired
    UserRepoService userRepoService;
    @Autowired
    RoleGroupRepoService roleGroupRepoService;
    @Autowired
    UserGroupRepoService userGroupRepoService;
    @Autowired
    FlushCacheService flushCacheService;

    @Override
	public String createGroup(GroupDTO groupDTO) {
		Group group = GroupDTOConverter.convert(groupDTO);
		//group.setDn(dn);
		groupRepoService.insert(group);
		return group.getId();
	}

    @Override
    public void updateGroup(GroupDTO groupDTO) {
        Group group = groupRepoService.findById(groupDTO.getId());
        int update = groupRepoService.update(GroupDTOConverter.convert(group, groupDTO));
        if(update!=1){
            throw new RuntimeException("更新组失败。id:"+group.getId());
        }
    }

    @Override
    public GroupDTO getGroup(String id) {
        Group group = groupRepoService.findById(id);
        return GroupConverter.convert(group);
    }
    

    @Override
    public PagedList<GroupDTO> findAllGroups(Paginator paginator) {
        List<Group> groups = groupRepoService.findAll();
        GroupComparator groupComparator = new GroupComparator();
        Collections.sort(groups, groupComparator);
        List<GroupDTO> gList=getGroupDtoList(groups);
        return PageListUtil.convert(paginator, gList);
    }

    @Override
    public List<GroupDTO> findGroupByUserId(String userId) {
        List<Group> groups = groupRepoService.findUser_UserGroup_GroupByUserId(userId);
        return getGroupDtoList(groups);
    }
    @Override
    public List<GroupDTO> findGroupsByName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("用户组名称不能为空");
        }
        List<Group> groups = groupRepoService.findGroupByName(name);
        return getGroupDtoList(groups);
    }

    @Override
    public PagedList<UserWithGroupDTO> findUserByGroupId(UserCriteriaDTO userCriteriaDTO, String groupId, Paginator paginator) {

        List<User> users = userRepoService.findByCriteriaDto(userCriteriaDTO,paginator);
        long totalCount = userRepoService.findCountByCriteriaDto(userCriteriaDTO);
        List<UserGroup> userGroups = userGroupRepoService.findUserGroupByGroupId(groupId);
        String groupUserIds = null;
        if(userGroups!=null && !userGroups.isEmpty()){
            StringBuffer sb = new StringBuffer();
            for (UserGroup userGroup : userGroups) {
                sb.append(userGroup.getUserId()).append(",");
            }
            groupUserIds = sb.toString();
        }
        List<UserWithGroupDTO> resultListDtos=new ArrayList<UserWithGroupDTO>();
        for (User user : users) {
        	UserWithGroupDTO temp=UserConverter.toUserWithGroupDTO(user);
            if(groupUserIds!=null){
                if(groupUserIds.contains(user.getId())){
                    temp.setHasThisGroup(true);
                }
            }
            resultListDtos.add(temp);
		}
        log.debug("result size:${result.size}");
        Collections.sort(resultListDtos,new Comparator<UserWithGroupDTO>() {
            @Override
            public int compare(UserWithGroupDTO o1, UserWithGroupDTO o2) {
                return o2.getHasThisGroup().toString().compareTo(o1.getHasThisGroup().toString());
            }});
        return new PagedList<UserWithGroupDTO>(totalCount, paginator, resultListDtos);
    }

    @Override
    public void enabledGroup(String groupId, Boolean isEnabled) {
        flushCacheService.modify++;
        Group group = groupRepoService.findById(groupId);
        group.setStatus(isEnabled);
        int update = groupRepoService.update(group);
        if(update!=1){
            throw new RuntimeException("更新组失败！id=："+group.getId());
        }
    }

    @Override
    public List<RoleDTO> findRolesByGroupId(String groupId) {
        List<RoleGroup> roleGroups = roleGroupRepoService.findRoleGroupsByGroupId(groupId);
        List<RoleDTO> result=new ArrayList<>();
        for (RoleGroup roleGroup : roleGroups) {
        	String roleId = roleGroup.getRoleId();
        	Role role = roleRepoService.findById(roleId);
        	if (role == null) {
                throw new RuntimeException("该角色不存在！");
            }
            result.add(RoleConverter.toRoleDTO(role));
		}
        return result;
    }

    @Override
    public void bindingUserWithGroups(String userId, List<String> groupIds) {
        //TODO 即先没有逻辑，暂时保存
    }

    @Override
    public void addUserToGroup(String userId, String groupId) {
        flushCacheService.modify++;
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(groupId)) {
            throw new RuntimeException("用户标识和用户组标识都不能为空！");
        }
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(groupId);
        userGroup.setUserId(userId);
        userGroup.setId(UUID.randomUUID().toString());

        List<UserGroup> userGroups = userGroupRepoService.findByUserIdGroupId(userId, groupId);
        if (userGroups!=null && userGroups.size()>0) {
            String userGroupId = userGroups.get(0).getId();
            userGroupRepoService.delete(userGroupId);//清除用户组中用户的数据
        }
        User user =userRepoService.findById(userId);
        if (user ==null) {
        	throw new RuntimeException("该用户已被删除！");
        }
        Group group = groupRepoService.findById(groupId);
        if (user.isStatus() && group.isStatus()) {
        	userGroup.setStatus(true);
        } else {
        	userGroup.setStatus(false);
        }
        int insert = userGroupRepoService.insert(userGroup);
        if(insert!=1){
            throw new RuntimeException("插入用户组失败！，用户组id："+userGroup.getId());
        }
    }

    @Override
   public void revokeUserToGroup(String userId, String groupId) {
        flushCacheService.modify++;
        List<UserGroup> userGroups = userGroupRepoService.findByUserIdGroupId(userId, groupId);
        if(userGroups==null||userGroups.isEmpty()){
            throw new RuntimeException("根据用户id和组id查询组失败！，userId："+userId +"  groupId:"+groupId);
        }
        String userGroupId = userGroups.get(0).getId();
        userGroupRepoService.delete(userGroupId);
    }

    @Override
    public void bindingRolesWithGroup(String groupId, List<String> roleIds) {
        flushCacheService.modify++;
        if (StringUtils.isEmpty(groupId)) {
            throw new RuntimeException("用户组标识不能为空！");
        }
       for (String roleId : roleIds) {
    	   RoleGroup roleGroup = new RoleGroup();
            roleGroup.setGroupId(groupId);
            roleGroup.setRoleId(roleId);
            roleGroup.setId( UUID.randomUUID().toString());
            Group group = groupRepoService.findById(groupId);

            List<RoleGroup> roleGroups = roleGroupRepoService.findByRoleIdGroupId(roleId, groupId);
            Role role =roleRepoService.findById(roleId);
            if (role == null) {
            	throw new RuntimeException("该角色不存在！");
            }
            if (roleGroups.isEmpty()) {
                if (role.isStatus() && group.isStatus()) {
                    roleGroup.setStatus(true);
                } else {
                    roleGroup.setStatus(false);
                }
                roleGroupRepoService.update(roleGroup);
            } else {
                RoleGroup ldapRoleGroup = roleGroups.get(0);
                if (role.isStatus() && group.isStatus()) {
                	ldapRoleGroup.setStatus(true);
                } else {
                	ldapRoleGroup.setStatus(false);
                }
                roleGroupRepoService.update(ldapRoleGroup);
            }

	    }

    }
    private List<GroupDTO> getGroupDtoList(List<Group> groups){
        if(groups==null||groups.isEmpty()){
            return new ArrayList<GroupDTO>();
        }
        List<GroupDTO> gList=new ArrayList<>();
        for (Group group : groups) {
            GroupDTO groupDTO = GroupConverter.convert(group);
            gList.add(groupDTO);
        }
        return gList;
    }
}
