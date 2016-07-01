package com.shangpin.uaas.services.dao.mapper;

import com.shangpin.uaas.entity.RoleGroup;
import com.shangpin.uaas.entity.example.RoleGroupExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleGroupMapper {
    int countByExample(RoleGroupExample example);

    int deleteByExample(RoleGroupExample example);

    int deleteByPrimaryKey(String id);

    int insert(RoleGroup record);

    int insertSelective(RoleGroup record);

    List<RoleGroup> selectByExample(RoleGroupExample example);

    RoleGroup selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RoleGroup record, @Param("example") RoleGroupExample example);

    int updateByExample(@Param("record") RoleGroup record, @Param("example") RoleGroupExample example);

    int updateByPrimaryKeySelective(RoleGroup record);

    int updateByPrimaryKey(RoleGroup record);
}