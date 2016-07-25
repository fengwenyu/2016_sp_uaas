package com.shangpin.uaas.services.dao;

import com.shangpin.uaas.entity.Resource;
import com.shangpin.uaas.entity.example.ResourceExample;
import com.shangpin.uaas.services.dao.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.ByteArrayPropertyEditor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源
 */
//@Transactional
@Service
public class ResourceRepoService {
    @Autowired
    private ResourceMapper resourceMapper;
    /**
     * 创建资源
     *
     * @param resource
     * @return
     */
	public int createResource(Resource resource){
        return resourceMapper.insert(resource);
    }

    /**
     * 更新资源
     *
     * @param resource
     */
	public int updateResource(Resource resource) {
        int update = resourceMapper.updateByPrimaryKey(resource);
        return update;
    }
    /**
     * 删除资源
     *
     */
	public int delete(String resourceId){
        int delete = resourceMapper.deleteByPrimaryKey(resourceId);
        return delete;
    }

    /**
     * 根据资源级别查询
     *
     * @return
	public List<Resource> findByLevel(String level);
     */

   

//	@Query("update #{#entityName} set enabled=?2 where uuid=?1 ")
	//public int modifyEnable(String resourceId, boolean b) {return 0;}

    /**
     * 根据资源ID茶中资源
     *
     * @param resourceId
     * @return
     */
   	//@Cacheable(value = "defaultCache" , key = "'UAAS-RESOURCE:' + #resourceId")
    public Resource findById(String resourceId){
        return resourceMapper.selectByPrimaryKey(resourceId);
    }
    public List<Resource> findByIdIn(List<String> resourceIds){
        ResourceExample example = new ResourceExample();
        ResourceExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(resourceIds);
        return resourceMapper.selectByExample(example);
    }
    public List<Resource> findCanuseByIdIn(List<String> resourceIds){
        ResourceExample example = new ResourceExample();
        ResourceExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(resourceIds);
        criteria.andEnabledEqualTo(Byte.parseByte("1"));
        return resourceMapper.selectByExample(example);
    }

   // public void modifyResource(Resource resource){}

    /**
     * 根据url查找资源
     * @param uri
     * @return
     */
    public List<Resource> findResourcesByUri(String uri){
        ResourceExample example = new ResourceExample();
        ResourceExample.Criteria criteria = example.createCriteria();
        criteria.andUriEqualTo(uri);
        return resourceMapper.selectByExample(example);
    }
    public List<Resource> findResourcesByUserId(String userId){
        return resourceMapper.findResourcesByUserId(userId);
    }
    public List<Resource> findResourcesByRoleId(String roleId){
        return resourceMapper.findResourcesByRoleId(roleId);
    }


    /**
     * 根据状态查询
     *
     * @param b
     * @return
     */
//    @Query("from #{#entityName} where enabled=?1")
   // public List<Resource> findResourceByState(boolean b) {return null;}
 //  public Resource findOne(String id){return null;}
  // public void save(Resource resource){}
   //public List<Resource> findAll(Set<String> set){return null;}
}
