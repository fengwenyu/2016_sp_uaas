package com.shangpin.uaas.services.admin;

import com.shangpin.uaas.api.admin.resource.*;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;
import com.shangpin.uaas.convert.ResourceNodeConverter;
import com.shangpin.uaas.convert.ResourceNodeDTOConverter;
import com.shangpin.uaas.entity.Permission;
import com.shangpin.uaas.entity.Resource;
import com.shangpin.uaas.entity.ResourceNode;
import com.shangpin.uaas.services.dao.PermissionRepoService;
import com.shangpin.uaas.services.dao.ResourceNodeRepoService;
import com.shangpin.uaas.services.dao.ResourceRepoService;
import com.shangpin.uaas.util.PageListUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 */
@Service
public class ResourceNodeAdminFacadeService implements ResourceNodeAdminFacade {
    protected static Logger log = LoggerFactory.getLogger(ResourceNodeAdminFacadeService.class);
    @Autowired
    private ResourceRepoService resourceRepoService;
    @Autowired
    private ResourceNodeRepoService resourceNodeRepoService;
    @Autowired
    private  PermissionRepoService permissionRepoService;

    @Override
    public List<ResourceNodeDTO> findResourceNodeByParentResourceId(String parentResourceId) {
        List<ResourceNode> resourceNodes = resourceNodeRepoService.findByParentResourceId(parentResourceId);

        List<ResourceNodeDTO> resourceNodeDTOs = new ArrayList<>(resourceNodes.size());
        for (ResourceNode resourceNode : resourceNodes) {
            ResourceNodeDTO result=ResourceNodeConverter.toResourceNodeDTO(resourceNode);
            //TODO use in to find all
            Resource resource = resourceRepoService.findById(resourceNode.getResourceId());
            if (resource == null) {
                log.error("没有该资源：" + resourceNode.getResourceId());
                //  throw new RuntimeException("没有该资源：" + resourceNode.getResourceId());
            }else{
                result.setIsEnabled(resource.isEnabled());
                result.setUri(resource.getUri());
                resourceNodeDTOs.add(result);
            }
        }
        log.debug("所有资源：" + resourceNodeDTOs.size());
        return resourceNodeDTOs;
    }

    @Override
    public  List<ResourceNodeWithFunctionsDTO> findResourceNodeWithFunctionsByParentResourceId(String parentResourceId) {
        List<ResourceNodeDTO> resourceNodeDTOs = this.findResourceNodeByParentResourceId(parentResourceId);
        if(CollectionUtils.isEmpty(resourceNodeDTOs))
            return null;
        List<ResourceNodeWithFunctionsDTO> resWithFunDTOs=new ArrayList<>(resourceNodeDTOs.size());
        for (ResourceNodeDTO parentResDTO : resourceNodeDTOs) {
            if(!parentResDTO.getType().equals(ResourceType.BUTTON)){
                List<ResourceNodeDTO> subResourceNodeDTOs=findResourceNodeByParentResourceId(parentResDTO.getId());
                List<FunctionDTO> functions = new ArrayList<>(subResourceNodeDTOs.size());
                for (ResourceNodeDTO subResDTO : subResourceNodeDTOs) {
                    if(ResourceType.BUTTON.equals(subResDTO.getType())){
                        functions.add(new FunctionDTO(subResDTO,false));
                    }
                }
                boolean isHasChildren = (functions.size() < subResourceNodeDTOs.size());
                FunctionDTO defaultFunction = new FunctionDTO(parentResDTO, false);
                defaultFunction.setName("浏览");
                functions.add(defaultFunction);
                resWithFunDTOs.add(new ResourceNodeWithFunctionsDTO(parentResDTO, functions, isHasChildren));
            }
        }
        return resWithFunDTOs;
    }

    @Override
    public PagedList<ResourceNodeDTO> findResourceNodesByRoleId(List<String> roleIds, Paginator paginator) {
        log.debug("角色数：" + roleIds.size());
        if (roleIds.isEmpty()) {
            return PageListUtil.convert(paginator, new ArrayList<ResourceNodeDTO>());
        }

        List<Permission> allPermissions = new ArrayList<>();
        List<Permission> byRoleIdIn = permissionRepoService.findByRoleIdIn(roleIds);
        allPermissions.addAll(byRoleIdIn);
        Set<String> resourceIds = new HashSet<>();
        for (Permission permission : allPermissions) {
            resourceIds.add(permission.getResourceId());
        }

        log.debug("查询出所有的资源数:" + resourceIds.size());
        List<ResourceNode> resourceNodes = new ArrayList<>();
        if( resourceIds.isEmpty()){
            return PageListUtil.convert(paginator, new ArrayList<ResourceNodeDTO>());
        }
        List<ResourceNode> byResourceIdIn = resourceNodeRepoService.findByResourceIdIn(new ArrayList<>(resourceIds));
        resourceNodes.addAll(byResourceIdIn);
        log.debug("查询出所有的资源Node数:" + resourceNodes.size());
        List<ResourceNodeDTO> result=new ArrayList<>();
        for (ResourceNode resourceNode : resourceNodes) {
            ResourceNodeDTO node=ResourceNodeConverter.toResourceNodeDTO(resourceNode);
           // Resource resource = resourceRepoService.findById(resourceNode.getResourceId());
//            if (resource == null) {
//                throw new RuntimeException("没有该资源：" + resourceNode.getResourceId());
//                log.error("没有该resource ，resourceid："+resourceNode.getResourceId());
//            }else{
//            }
            node.setIsEnabled("1".equals(resourceNode.getEnabled()));
            node.setUri(resourceNode.getUri());
            result.add(node);
        }
        log.debug("查询的节点总数为：" + result.size());
        return PageListUtil.convert(paginator, result);
    }

    /**
     * 根据节点名称获取所有的子节点
     * @param modelName 节点名
     * @throws Exception
     */
    public List<ResourceNodeDTO> getSubResourcesByParentResourcesName(String modelName) {
        List<ResourceNode> byModuleName;
        if(StringUtils.isBlank(modelName)){
            byModuleName = resourceNodeRepoService.findByParentResourceId("1");
        }else{
            byModuleName = resourceNodeRepoService.findByModuleName(modelName.trim());
        }
        if(byModuleName==null||byModuleName.isEmpty()){
            log.info("根据name："+modelName+"查询resourceNode查不到任何资源");
            return new ArrayList<>();
        }
        List<ResourceNodeDTO> result = new ArrayList<>();
        for (ResourceNode node : byModuleName) {
            List<ResourceNode> resourceNodes = getAllChildResourceNode(node, new ArrayList<ResourceNode>());
            for (ResourceNode resourceNode : resourceNodes) {
                Resource byId = resourceRepoService.findById(resourceNode.getResourceId());
                if(byId!=null){
                    resourceNode.setEnabled(byId.getId());
                    resourceNode.setUri(byId.getUri());
                }
                result.add(ResourceNodeConverter.toResourceNodeDTO(resourceNode));
            }
        }
        /*String parentId = byModuleName.get(0).getId();
        List<ResourceNode> byParentResourceId = resourceNodeRepoService.findByParentResourceId(parentId);

        for (ResourceNode node : byParentResourceId) {
            ResourceNodeDTO resourceNodeDTO = ResourceNodeConverter.toResourceNodeDTO(node);
            result.add(resourceNodeDTO);
        }*/
        return result;

    }

    @Override
    public List<ResourceNodeDTO> findResourceNodesByCriteria(ResourceNodeCriteriaDTO resourceNodeCriteriaDTO) {
        String nameLike = org.apache.commons.lang3.StringUtils.isBlank(resourceNodeCriteriaDTO.getNameLike())?null:resourceNodeCriteriaDTO.getNameLike();
        String isEnabled;
        if(org.apache.commons.lang3.StringUtils.isBlank(resourceNodeCriteriaDTO.getIsEnabled())){
            isEnabled=null;
        }else{
            isEnabled="true".equals(resourceNodeCriteriaDTO.getIsEnabled())?"1":"0";
        }

        log.debug("＝＝＝＝＝＝查询条件是＝＝＝＝＝＝nameLike : ${nameLike} ======isEnabled:${isEnabled}");
        resourceNodeCriteriaDTO.setIsEnabled(isEnabled);
        resourceNodeCriteriaDTO.setNameLike(nameLike);
        List<ResourceNode> resourceNodes = resourceNodeRepoService.findByResourceNodeCriteriaDTO(resourceNodeCriteriaDTO);

        List<ResourceNodeDTO> resourceNodeDTOs=new ArrayList<>();
        if(!resourceNodes.isEmpty()){
            for (ResourceNode resourceNode : resourceNodes) {
                resourceNodeDTOs.add(ResourceNodeConverter.toResourceNodeDTO(resourceNode));
            }
        }
        return resourceNodeDTOs;
      /*  List<ResourceNode> resourceNodes ;

        if (StringUtils.isEmpty(nameLike)) {
            resourceNodes = resourceNodeRepoService.findAll();
        } else {
            resourceNodes = resourceNodeRepoService.findByModuleNameLike(nameLike);
        }
        List<ResourceNodeDTO> resourceNodeDTOs=new ArrayList<>();
        for (ResourceNode resourceNode : resourceNodes) {
        	resourceNodeDTOs.add(ResourceNodeConverter.toResourceNodeDTO(resourceNode));
		}
        for (ResourceNodeDTO resourceNodeDTO : resourceNodeDTOs) {
        	Resource resource = resourceRepoService.findById(resourceNodeDTO.getResourceId());
            if (resource != null) {
                resourceNodeDTO.setIsEnabled(resource.isEnabled());
                resourceNodeDTO.setUri(resource.getUri());
                break;
            }
		}
		*/
        /*log.debug("==================resourceNodeDTOs:${resourceNodeDTOs.size()}");
        List<ResourceNodeDTO> result =  new ArrayList<ResourceNodeDTO>(resourceNodeDTOs.size());
        if (!StringUtils.isEmpty(isEnabled)) {
            log.debug("==================resourceNodeDTOs:${resourceNodeDTOs.size()}=======isEnabled:${isEnabled}");
            for (ResourceNodeDTO resourceNodeDTO : resourceNodeDTOs) {
            	 log.debug("==================resourceNodeDTO:${resourceNodeDTO.isEnabled}=======isEnabled:${isEnabled}");
                 if (isEnabled.equals(String.valueOf(resourceNodeDTO.getIsEnabled()))) {
                     result.add(resourceNodeDTO);
                 }
			}
        } else {
            result.addAll(resourceNodeDTOs);
        }*/

       /* log.debug("==================最终result: ${result.size()}");
        return result;*/
    }

    public void createResource(ResourceNodeDTO resourceNodeDTO) throws Exception{
        String uri = resourceNodeDTO.getUri();
        List<Resource> resourcesByUri = resourceRepoService.findResourcesByUri(uri);
        if(resourcesByUri!=null && resourcesByUri.size()>0){
            throw new Exception("该uri已经存在，uri："+uri+"    拥有该uri的resourceId："+resourcesByUri.get(0).getId());
        }
        Resource resource = new Resource();
        resource.setId(UUID.randomUUID().toString());
        resource.setUri(uri);
        resource.setEnabled(true);
        int insert = resourceRepoService.createResource(resource);
        if(insert!=1){
            throw new Exception("插入资源失败。resourceId："+resource.getId());
        }
        ResourceNode node = new ResourceNode();
        node.setId(resourceNodeDTO.getId());
        node.setResourceId(resource.getId());
        node = ResourceNodeDTOConverter.toResourceNode(node, resourceNodeDTO);
        int insert2 = resourceNodeRepoService.insert(node);
        if(insert2!=1){
            throw new Exception("插入资源Node失败。resourceNodeId："+node.getId());
        }
    }


    public List<ResourceNode> getAllChildResourceNode(ResourceNode resourceNode,List<ResourceNode> nodes){
        nodes.add(resourceNode);
        //获取所有的子节点
        List<ResourceNode> resourceNodes = resourceNodeRepoService.findByParentResourceId(resourceNode.getId());
        if(!resourceNodes.isEmpty()){
            for (ResourceNode node : resourceNodes) {
                getAllChildResourceNode(node,nodes);
            }
        }
        return nodes;
    }
}
