package com.shangpin.uaas.services.dao;

import com.shangpin.uaas.api.admin.user.Status;
import com.shangpin.uaas.api.admin.user.UserCriteriaDTO;
import com.shangpin.uaas.api.common.Paginator;
import com.shangpin.uaas.entity.User;
import com.shangpin.uaas.entity.example.UserExample;
import com.shangpin.uaas.services.dao.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 *
 */
//@Transactional
@Service
public class UserRepoService/* extends Mapper<User>*/{
    @Autowired
    private UserMapper userMapper;
    public int save(User user){
        return userMapper.insert(user);
    }
    public List<User> findByUsername(String username){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        return  userMapper.selectByExample(userExample);
    }

    public int update(User user){
        return userMapper.updateByPrimaryKey(user);
    }

    public int delete(User user){
        return userMapper.deleteByPrimaryKey(user.getId());
    }

    public List<User> findByMobile(String mobile){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andMobileLike("%"+mobile+"%");
        return userMapper.selectByExample(userExample);
    }

    public List<User> findByEmail(String email) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailLike("%"+email+"%");
        return userMapper.selectByExample(userExample);
    }

    public List<User> findByOrganizationId(String orgId){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOrganizationidEqualTo(orgId);
        return userMapper.selectByExample(userExample);
    }

    /**
     * 根据用户的UUID获取用户
     *
     */
    public  User findById(String uuid){
        return userMapper.selectByPrimaryKey(uuid);
    }
    public  List<User> findByIdIn(List<String> userIds){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdIn(userIds);
        return userMapper.selectByExample(userExample);
    }

    /**
     * 模糊查询真实姓名的用户
     *
     */
//    List<User> findByRealNameContaining(String realname) {return null;}

    public List<User> findAll() {
        List<User> all = userMapper.findAll();
        return all;
    }
   

   /* public void getUser(String dn) {
        Name name = LdapName(dn);
        return ldapTemplate.findByDn(name, User.class)
    }*/

    public List<User> findByCriteriaDto(UserCriteriaDTO userCriteriaDTO, Paginator paginator){
        int start =(int) paginator.getOffset();
        int size = paginator.getPageSize();
        Status status = userCriteriaDTO.getStatus();
        if(status ==null){
            return userMapper.findByCriteriaDto(defaultUserCriteriaDTO(userCriteriaDTO),null,start,size);
        }else{
            return userMapper.findByCriteriaDto(defaultUserCriteriaDTO(userCriteriaDTO),"ENABLED".equals(status.toString())?"1":"0",start,size);
        }
    }


    public List<User> findAllUsersWithRoleByCriteria(UserCriteriaDTO userCriteriaDTO,String roleId, Paginator paginator){
        int start =(int) paginator.getOffset();
        int size = paginator.getPageSize();
        Status status = userCriteriaDTO.getStatus();
        if(status ==null){
            return userMapper.findAllUsersWithRoleByCriteria(defaultUserCriteriaDTO(userCriteriaDTO),null,roleId,start,size);
        }else{
            return userMapper.findAllUsersWithRoleByCriteria(defaultUserCriteriaDTO(userCriteriaDTO),"ENABLED".equals(status.toString())?"1":"0",roleId,start,size);
        }
    }
    public List<User> findAllUsersWithRoleByCriteriaAndStatusNull(UserCriteriaDTO userCriteriaDTO,String roleId, Paginator paginator){
        int start =(int) paginator.getOffset();
        int size = paginator.getPageSize();
        Status status = userCriteriaDTO.getStatus();
        if(status ==null){
            return userMapper.findAllUsersWithRoleByCriteriaAndStatusNull(defaultUserCriteriaDTO(userCriteriaDTO),null,roleId,start,size);
        }else{
            return userMapper.findAllUsersWithRoleByCriteriaAndStatusNull(defaultUserCriteriaDTO(userCriteriaDTO),"ENABLED".equals(status.toString())?"1":"0",roleId,start,size);
        }
    }
    public long findCountAllUsersWithRoleByCriteria(UserCriteriaDTO userCriteriaDTO,String roleId){
        Status status = userCriteriaDTO.getStatus();
        if(status ==null){
            return userMapper.findCountAllUsersWithRoleByCriteria(defaultUserCriteriaDTO(userCriteriaDTO),null,roleId);
        }else{
            return userMapper.findCountAllUsersWithRoleByCriteria(defaultUserCriteriaDTO(userCriteriaDTO),"ENABLED".equals(status.toString())?"1":"0",roleId);
        }
    }
    public List<User> findAllUsersWithNotHaveRoleByCriteria(UserCriteriaDTO userCriteriaDTO,String roleId, Paginator paginator){
        int start =(int) paginator.getOffset();
        int size = paginator.getPageSize();
        Status status = userCriteriaDTO.getStatus();
        if(status ==null){
            return userMapper.findAllUsersWithNotHaveRoleByCriteria(defaultUserCriteriaDTO(userCriteriaDTO),null,roleId,start,size);
        }else{
            return userMapper.findAllUsersWithNotHaveRoleByCriteria(defaultUserCriteriaDTO(userCriteriaDTO),"ENABLED".equals(status.toString())?"1":"0",roleId,start,size);
        }
    }
    public long findCountAllUsersWithNotHaveRoleByCriteria(UserCriteriaDTO userCriteriaDTO,String roleId){
        Status status = userCriteriaDTO.getStatus();
        if(status ==null){
            return userMapper.findCountAllUsersWithNotHaveRoleByCriteria(defaultUserCriteriaDTO(userCriteriaDTO),null,roleId);
        }else{
            return userMapper.findCountAllUsersWithNotHaveRoleByCriteria(defaultUserCriteriaDTO(userCriteriaDTO),"ENABLED".equals(status.toString())?"1":"0",roleId);
        }
    }


    public long findCountByCriteriaDto(UserCriteriaDTO userCriteriaDTO){
        Status status = userCriteriaDTO.getStatus();
        if(status ==null){
            return userMapper.findCountByCriteriaDto(defaultUserCriteriaDTO(userCriteriaDTO),null);
        }else{
            return userMapper.findCountByCriteriaDto(defaultUserCriteriaDTO(userCriteriaDTO),"ENABLED".equals(status.toString())?"1":"0");
        }
    }
    /*{
        ldapTemplate.rename(oldDn, newDn);
    }*/

    public List<User> findByUserCode(String userCode) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsercodeEqualTo(userCode);
        return userMapper.selectByExample(userExample);
    }

  /*  public User findOne(String id){

    }*/
    /* {
        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return ldapTemplate.search(base, "(objectclass=UAASUser)", new LdapObjectAttributesMapper());
    }*/
/*
    private class LdapObjectAttributesMapper implements AttributesMapper  {
        public Object mapFromAttributes(Attributes attrs) {
        	try {
        		User user = new User();
        		user.setGender(getAttribute(attrs, "UAASUserGender"));
        		user.setStatus(getAttribute(attrs, "UAASUserStatus"));
        		user.setUsername(getAttribute(attrs, "cn"));
        		user.setCreatedTime(getAttribute(attrs, "UAASUserCreatedTime"));
        		user.setDirectLeaderId(getAttribute(attrs, "UAASUserDirectLeader"));
        		user.setEmail(getAttribute(attrs, "mail"));
        		user.setJobLevel(getAttribute(attrs, "UAASUserJobLevel"));
        		user.setOrganizationId(getAttribute(attrs, "UAASUserOrganizationID"));
        		user.setPassword(getAttribute(attrs, "userPassword"));
        		user.setUserCode(getAttribute(attrs, "uid"));
        		user.setId(getAttribute(attrs, "UAASUserUUID"));
        		user.setRealName(getAttribute(attrs, "sn"));
        		user.setMobile(getAttribute(attrs, "mobile"));
        		user.setWorkplace(getAttribute(attrs, "UAASUserWorkplace"));
        		// 返回封装后的用户对象
        		return user;
			} catch (Exception e) {
				return null;
			}
        }
    }

    *//**
     * 从属性列表中获取指定的属性
     *
     *            属性列表
     *            需要获取的属性
     * @return 返回获取的属性值
     *//*
    private String getAttribute(Attributes attrs, String attrName)
            throws NamingException {
        Attribute attr = attrs.get(attrName);
        // 若没有指定的属性返回空字符串
        if (attr == null) {
            return "";
        } else {
            return (String) attr.get();
        }
    }*/
    public UserCriteriaDTO  defaultUserCriteriaDTO(UserCriteriaDTO userCriteriaDTO){
        if(userCriteriaDTO==null){
            return null;
        }
        if(userCriteriaDTO.getGender()!=null){
            userCriteriaDTO.setGender(StringUtils.isNotBlank(userCriteriaDTO.getGender().toString())?userCriteriaDTO.getGender():null);
        }
        userCriteriaDTO.setOrganizationId(StringUtils.isNotBlank(userCriteriaDTO.getOrganizationId())?userCriteriaDTO.getOrganizationId():null);
        userCriteriaDTO.setOrganizationNameLike(StringUtils.isNotBlank(userCriteriaDTO.getOrganizationNameLike())?userCriteriaDTO.getOrganizationNameLike():null);
        userCriteriaDTO.setRealNameLike(StringUtils.isNotBlank(userCriteriaDTO.getRealNameLike())?userCriteriaDTO.getRealNameLike():null);
        if(userCriteriaDTO.getStatus()!=null){
            userCriteriaDTO.setStatus(StringUtils.isNotBlank(userCriteriaDTO.getStatus().toString())?userCriteriaDTO.getStatus():null);
        }
        userCriteriaDTO.setWorkLocation(StringUtils.isNotBlank(userCriteriaDTO.getWorkLocation())?userCriteriaDTO.getWorkLocation():null);
        userCriteriaDTO.setOrganizationIds((userCriteriaDTO.getOrganizationIds()==null ||userCriteriaDTO.getOrganizationIds().isEmpty())?null:userCriteriaDTO.getOrganizationIds());
        return userCriteriaDTO;
    }
}