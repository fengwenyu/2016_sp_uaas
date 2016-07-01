package com.shangpin.uaas.services.dao.mapper;

import com.shangpin.uaas.entity.Resource;
import com.shangpin.uaas.entity.example.ResourceExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResourceMapper {
    int countByExample(ResourceExample example);

    int deleteByExample(ResourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(Resource record);

    int insertSelective(Resource record);

    List<Resource> selectByExample(ResourceExample example);
    List<Resource> findResourcesByUserId(@Param("userId") String userId);
    List<Resource> findResourcesByRoleId(@Param("roleId") String roleId);

    Resource selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
}