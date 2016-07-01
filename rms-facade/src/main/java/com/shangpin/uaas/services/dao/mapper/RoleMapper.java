package com.shangpin.uaas.services.dao.mapper;

import com.shangpin.uaas.api.admin.role.RoleCriteria;
import com.shangpin.uaas.entity.Role;
import com.shangpin.uaas.entity.example.RoleExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);
    List<Role> findAll();
    List<Role> findByUserId(@Param("userId") String userId);
    List<Role> findByCriteriaDto(@Param("roleCriteria") RoleCriteria roleCriteria,@Param("status") String status,@Param("start") int start,@Param("size")int size);
    long findCountByCriteriaDto(@Param("roleCriteria") RoleCriteria roleCriteria,@Param("status") String status);

    Role selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}