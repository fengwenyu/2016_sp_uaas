package com.shangpin.uaas.services.dao.mapper;

import com.shangpin.uaas.api.admin.user.UserCriteriaDTO;
import com.shangpin.uaas.entity.User;
import com.shangpin.uaas.entity.example.UserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> findAll();

    List<User> findByCriteriaDto(@Param("userDto") UserCriteriaDTO userCriteriaDTO,@Param("userStatus") String status,@Param("start")int start,@Param("size")int size);
    List<User> findAllUsersWithRoleByCriteria(@Param("userDto") UserCriteriaDTO userCriteriaDTO,@Param("userStatus") String status,@Param("roleId") String roleId,@Param("start")int start,@Param("size")int size);
    List<User> findAllUsersWithRoleByCriteriaAndStatusNull(@Param("userDto") UserCriteriaDTO userCriteriaDTO,@Param("userStatus") String status,@Param("roleId") String roleId,@Param("start")int start,@Param("size")int size);
    long findCountAllUsersWithRoleByCriteria(@Param("userDto") UserCriteriaDTO userCriteriaDTO,@Param("userStatus") String status,@Param("roleId") String roleId);
    List<User> findAllUsersWithNotHaveRoleByCriteria(@Param("userDto") UserCriteriaDTO userCriteriaDTO,@Param("userStatus") String status,@Param("roleId") String roleId,@Param("start")int start,@Param("size")int size);
    long findCountAllUsersWithNotHaveRoleByCriteria(@Param("userDto") UserCriteriaDTO userCriteriaDTO,@Param("userStatus") String status,@Param("roleId") String roleId);
    long findCountByCriteriaDto(@Param("userDto") UserCriteriaDTO userCriteriaDTO,@Param("userStatus") String status);

}