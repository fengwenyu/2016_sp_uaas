package com.shangpin.uaas.services.dao;

import com.shangpin.uaas.api.admin.resource.ResourceNodeCriteriaDTO;
import com.shangpin.uaas.entity.ResourceNode;
import com.shangpin.uaas.entity.example.ResourceNodeExample;
import com.shangpin.uaas.services.dao.mapper.ResourceNodeMapper;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 资源
 */
//@Repository
//@Transactional
@Service
public class ResourceNodeRepoService {
    @Autowired
    private ResourceNodeMapper resourceNodeMapper;

    /**
     * 删除资源
     *
     */
    public int delete(String resourceNodeId){
        int delete = resourceNodeMapper.deleteByPrimaryKey(resourceNodeId);
        return delete;
    }
    public int insert(ResourceNode resourceNode){
        return resourceNodeMapper.insert(resourceNode);
    }

    public int updateResource(ResourceNode resourceNode){
        int update = resourceNodeMapper.updateByPrimaryKey(resourceNode);
        return update;
    }

    /**
     * 根据父级标识查询资源
     *
     * @param parentResourceNodeId
     * @return
     */
    public List<ResourceNode> findByParentResourceId(String parentResourceNodeId){
        ResourceNodeExample example = new ResourceNodeExample();
        ResourceNodeExample.Criteria criteria = example.createCriteria();
        criteria.andParentresourceidEqualTo(parentResourceNodeId);
        return resourceNodeMapper.selectByExample(example);
    }
    public List<ResourceNode> findByModuleNameAndParentId(String moduleName,String parentResourceNodeId){
        ResourceNodeExample example = new ResourceNodeExample();
        ResourceNodeExample.Criteria criteria = example.createCriteria();
        criteria.andParentresourceidEqualTo(parentResourceNodeId);
        criteria.andModulenameEqualTo(moduleName);
        return resourceNodeMapper.selectByExample(example);
    }


    /**
     * 创建资源组
     *
     * @param resourceNode
     */
    //public void createResourceNode(ResourceNode resourceNode){}

    /**
     * 根据资源ID茶中资源
     *
     * @param resourceId
     * @return
     */
    public List<ResourceNode> findByResourceId(String resourceId) {
        ResourceNodeExample example = new ResourceNodeExample();
        ResourceNodeExample.Criteria criteria = example.createCriteria();
        criteria.andResourceidEqualTo(resourceId);
        return resourceNodeMapper.selectByExample(example);
    }
    public List<ResourceNode> findByResourceIdIn(List<String> resourceIds) {
        ResourceNodeExample example = new ResourceNodeExample();
        ResourceNodeExample.Criteria criteria = example.createCriteria();
        criteria.andResourceidIn(resourceIds);
        return resourceNodeMapper.selectByExample(example);
    }

    //  public List<ResourceNode> findByResourceIdIn(Collection<String> resourceIds){return null;}

    public  ResourceNode findById(String resourceNodeId) {
        return resourceNodeMapper.selectByPrimaryKey(resourceNodeId);
    }

    /**
     * 更新资源组
     *
     * @param resourceNode
     * @return
     */
    // public void modifyResourceNode(ResourceNode resourceNode){}

    /**
     * 多条件查询
     *
     * @param resourceId
     * @param nameLike
     * @return
     */
    // public List<ResourceNode> findByCriteria(String resourceId, String nameLike){return null;}

    /**
     * 移动资源组
     public void moveResourceNode(String sourceDn, String descDn) {
     log.debug("=============sourceDn:${sourceDn};descDn:${descDn}");
     ldapTemplate.rename(sourceDn, descDn);
     }
     */

    public List<ResourceNode> findByModuleName(String name){
        ResourceNodeExample example = new ResourceNodeExample();
        ResourceNodeExample.Criteria criteria = example.createCriteria();
        criteria.andModulenameEqualTo(name);
        return resourceNodeMapper.selectByExample(example);
    }

    //public List<ResourceNode> findByModuleName(String resourdeNodeName){}
    //  public ResourceNode findOne(String id){return null;}
    //  public void save(ResourceNode resourceNodeResult){}
    public List<ResourceNode> findAll(){
        return resourceNodeMapper.findAll();
    }
    public List<ResourceNode> findAllInNodeIds(List<String> resourceNodeIds){
        ResourceNodeExample example = new ResourceNodeExample();
        ResourceNodeExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(resourceNodeIds);
        return resourceNodeMapper.selectByExample(example);
    }

    public List<ResourceNode> findByModuleNameLike(String nameLike){
        ResourceNodeExample example = new ResourceNodeExample();
        ResourceNodeExample.Criteria criteria = example.createCriteria();
        criteria.andModulenameLike("%"+nameLike+"%");
        return resourceNodeMapper.selectByExample(example);
    }
    public List<ResourceNode> findByResourceNodeCriteriaDTO(ResourceNodeCriteriaDTO resourceNodeCriteriaDTO){
        return resourceNodeMapper.findByResourceNodeCriteriaDTO(resourceNodeCriteriaDTO);
    }
}
