package com.shangpin.uaas.services.dao.mapper;

import com.shangpin.uaas.api.admin.resource.ResourceNodeCriteriaDTO;
import com.shangpin.uaas.entity.ResourceNode;
import com.shangpin.uaas.entity.example.ResourceNodeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResourceNodeMapper {
    int countByExample(ResourceNodeExample example);

    int deleteByExample(ResourceNodeExample example);

    int deleteByPrimaryKey(String id);

    int insert(ResourceNode record);

    int insertSelective(ResourceNode record);

    List<ResourceNode> selectByExample(ResourceNodeExample example);
    List<ResourceNode> findAll();
    List<ResourceNode> findByResourceNodeCriteriaDTO(@Param("resourceNodeDto") ResourceNodeCriteriaDTO resourceNodeCriteriaDTO);

    ResourceNode selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ResourceNode record, @Param("example") ResourceNodeExample example);

    int updateByExample(@Param("record") ResourceNode record, @Param("example") ResourceNodeExample example);

    int updateByPrimaryKeySelective(ResourceNode record);

    int updateByPrimaryKey(ResourceNode record);
}