package com.shangpin.uaas.services.admin;

import com.shangpin.uaas.api.admin.resource.ResourceAdminFacade;
import com.shangpin.uaas.api.admin.resource.ResourceNodeDTO;
import com.shangpin.uaas.convert.ResourceDTOConverter;
import com.shangpin.uaas.convert.ResourceNodeConverter;
import com.shangpin.uaas.convert.ResourceNodeDTOConverter;
import com.shangpin.uaas.entity.Permission;
import com.shangpin.uaas.entity.Resource;
import com.shangpin.uaas.entity.ResourceNode;
import com.shangpin.uaas.entity.Role;
import com.shangpin.uaas.services.api.MemcachedUtilService;
import com.shangpin.uaas.services.dao.PermissionRepoService;
import com.shangpin.uaas.services.dao.ResourceNodeRepoService;
import com.shangpin.uaas.services.dao.ResourceRepoService;
import com.shangpin.uaas.services.dao.RoleRepoService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 资源管理服务
 *
 */
@Transactional
@Service
class ResourceAdminFacadeService implements ResourceAdminFacade {
	protected static Logger log = LoggerFactory.getLogger(ResourceAdminFacadeService.class);
	//private static final String URI_HEAD="mvc-action://";  //uri判断逻辑用，由于后台数据问题，暂时屏蔽
	@Autowired
	private ResourceRepoService resourceRepoService;

	@Autowired
	private ResourceNodeRepoService resourceNodeRepoService;

	@Autowired
	private PermissionRepoService permissionRepoService;
	@Autowired
	private RoleRepoService roleRepoService;
	@Autowired
	private FlushCacheService flushCacheService;
	@Autowired
	RoleAdminFacadeService roleAdminService;
	@Autowired
	MemcachedUtilService memcachedUtilService;

	/**
	 * 创建资源（权限）
	 *
	 */
	@Override
	public String createResource(ResourceNodeDTO resourceNodeDTO) {
		try {
			String resourceNodeId ;
			// 校验参数中的ID是否有效，若有效则使用，否则由系统自动生成
			if (!StringUtils.isEmpty(resourceNodeDTO.getId())) {
				if ("1".equals(resourceNodeDTO.getId())) {
					return "该资源索引错误！";
				}
				ResourceNode resourceNodes = resourceNodeRepoService.findById(resourceNodeDTO.getId());
				if (null == resourceNodes) {
					return "该资源索引已存在";
				}
			} else {
				resourceNodeId = UUID.randomUUID().toString();
				resourceNodeDTO.setId(resourceNodeId);
			}

			Resource entity = ResourceDTOConverter.toCreateResource(resourceNodeDTO);
			ResourceNode entityNode = ResourceDTOConverter.toCreateResourceNode(resourceNodeDTO);
			String uri = resourceNodeDTO.getUri();
			if(org.apache.commons.lang3.StringUtils.isBlank(uri)){
				return "资源的url不能为空";
			}
			List<ResourceNode> byModuleNameAndParentId = resourceNodeRepoService.findByModuleNameAndParentId(resourceNodeDTO.getName(), resourceNodeDTO.getParentId());
			if(byModuleNameAndParentId!=null && byModuleNameAndParentId.size()>0){
				return "该位置已经有相同名字的资源了";
			}
			//检测url是否符合要求
			String parentId = resourceNodeDTO.getParentId();
			if(org.apache.commons.lang3.StringUtils.isBlank(parentId)){
				return "父资源不能为空！";
			}
			//uri判断逻辑，由于后台数据问题，暂时屏蔽
			/*if("1".equals(parentId)||"0".equals(parentId)){
				if(!uri.startsWith(URI_HEAD)){
					return "URI必须以 mvc-action://  开头！";
				}
			}else{
				ResourceNode resourceNode = resourceNodeRepoService.findById(resourceNodeDTO.getParentId());
				if (resourceNode == null) {
					return "父资源节点不存在！";
				}
				Resource ParentResource = resourceRepoService.findById(resourceNode.getResourceId());
				String parentUri = ParentResource.getUri();
				//如果父resource的rui不合法
				if(!parentUri.startsWith(URI_HEAD)){
					return "父资源   "+resourceNode.getModuleName()+"  的URI没有以  mvc-action://  开头！";
				}
				parentUri = parentUri.replace(URI_HEAD, "");
				int end = parentUri.indexOf("/");
				if(end<=0){
					return "父资源  "+resourceNode.getModuleName()+"  的URI "+ParentResource.getUri()+" 不规范，请修改！";
				}
				String appCode = parentUri.substring(0,end);
				String resourceUri = URI_HEAD+appCode+"/";
				if(!uri.startsWith(resourceUri)){
					return "URI应该以 "+resourceUri+"  开头";
				}
			}*/
			entityNode.setParentResourceId(resourceNodeDTO.getParentId());
			List<Resource> resources = resourceRepoService.findResourcesByUri(uri);
			if(resources==null ||resources.isEmpty()){
				int insert = resourceRepoService.createResource(entity);
				if(insert!=1){
					throw new RuntimeException("创建资源失败，id："+entity.getId());
				}
				entityNode.setResourceId(entity.getId());
			}else {
				List<ResourceNode> byResourceId = resourceNodeRepoService.findByResourceId(resources.get(0).getId());
				return "该URI已经被"+byResourceId.get(0).getModuleName()+"使用";
			}

			if(org.apache.commons.lang3.StringUtils.isBlank(entityNode.getId())){
				entityNode.setId(UUID.randomUUID().toString());
			}
			int insert = resourceNodeRepoService.insert(entityNode);
			if(insert!=1){
				throw new NotFoundException("创建资源Node失败，资源名：" + entityNode.getModuleName());
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}

	/**
	 * 获取资源
	 *
	 * @param resourceId
	 *            资源Node标识 必填
	 */
	@Override
	public ResourceNodeDTO getResourceNode(String resourceId) {
		log.debug("=====================resourceId:${resourceId}======================");
		ResourceNode resourceNode = resourceNodeRepoService.findById(resourceId);
		if (resourceNode==null) {
			throw new RuntimeException("没有相关资源");
		}
		Resource resource = resourceRepoService.findById(resourceNode.getResourceId());
		if (resource == null) {
			throw new RuntimeException("没有相关资源");
		}
		ResourceNodeDTO resourceNodeDTO = ResourceNodeConverter.toResourceNodeDTO(resourceNode);
		resourceNodeDTO.setUri(resource.getUri());
		return resourceNodeDTO;
	}

	@Override
	public String modifyResource(ResourceNodeDTO resourceNodeDTO) {
		try {
			log.debug("resourceNodeDTO :" + resourceNodeDTO.getIsEnabled());
			ResourceNode resourceNodeNow = resourceNodeRepoService.findById(resourceNodeDTO.getId());
			if (resourceNodeNow == null) {
				return "当前资源节点不存在！";
			}
			Resource resource = resourceRepoService.findById(resourceNodeNow.getResourceId());
			if (resource == null) {
				return "当前权限节点不存在！";
			}
			String parentId = resourceNodeDTO.getParentId();
			if(org.apache.commons.lang3.StringUtils.isBlank(parentId)){
				return "父资源不能为空！";
			}
			List<ResourceNode> byModuleNameAndParentId = resourceNodeRepoService.findByModuleNameAndParentId(resourceNodeDTO.getName(), parentId);
			if(byModuleNameAndParentId!=null && byModuleNameAndParentId.size()>0){
				for (ResourceNode node : byModuleNameAndParentId) {
					if(!node.getId().equals(resourceNodeDTO.getId())){
						return "该位置已经有相同名字的权限存在！";
					}
				}
			}

			String uri = resourceNodeDTO.getUri();
			if(org.apache.commons.lang3.StringUtils.isBlank(uri)){
				return "资源的url不能为空";
			}
			//uri判断逻辑，由于后台数据问题，暂时屏蔽
			/*//检测url是否符合要求
			if("1".equals(parentId)||"0".equals(parentId)){
				if(!uri.startsWith(URI_HEAD)){
					return "URI必须以 mvc-action://  开头！";
				}
			}else{
				ResourceNode resourceNode = resourceNodeRepoService.findById(resourceNodeDTO.getParentId());
				if (resourceNode == null) {
					return "父资源节点不存在！";
				}
				Resource ParentResource = resourceRepoService.findById(resourceNode.getResourceId());
				String parentUri = ParentResource.getUri();
				//如果父resource的rui不合法
				if(!parentUri.startsWith(URI_HEAD)){
					return "父资源   "+resourceNode.getModuleName()+"  的URI没有以  mvc-action://  开头！";
				}
				parentUri = parentUri.replace(URI_HEAD, "");
				int end = parentUri.indexOf("/");
				if(end<=0){
					return "父资源  "+resourceNode.getModuleName()+"  的URI "+ParentResource.getUri()+" 不规范，请修改！";
				}
				String appCode = parentUri.substring(0,end);
				String resourceUri = URI_HEAD+appCode+"/";
				if(!uri.startsWith(resourceUri)){
					return "URI应该以 "+resourceUri+"  开头";
				}
			}*/

			if(!resource.getUri().equals(resourceNodeDTO.getUri())){
				List<Resource> resourcesByUri = resourceRepoService.findResourcesByUri(resourceNodeDTO.getUri());
				if(resourcesByUri!=null &&resourcesByUri.size()>0){
					for (Resource resource1 : resourcesByUri) {
						if(!resource1.getId().equals(resourceNodeDTO.getResourceId())){
							List<ResourceNode> byResourceId = resourceNodeRepoService.findByResourceId(resource1.getId());
							return "该uri已被权限 "+byResourceId.get(0).getModuleName() +" 占用！";
						}
					}
				}
			}

			ResourceNode resourceNodeResult = ResourceNodeDTOConverter.toResourceNode(resourceNodeNow, resourceNodeDTO);

			Resource resourceResult = ResourceNodeDTOConverter.toResource(resource, resourceNodeDTO);

			log.debug("资源状态为：" + resourceResult.isEnabled());
			int updateResource = resourceRepoService.updateResource(resourceResult);
			if(updateResource!=1){
				throw new RuntimeException("更新资源失败，资源id："+resourceNodeResult.getId());
			}
			log.debug("DN~~~~~~~~~~~~~~$resourceNodeResult.dn");
			int update = resourceNodeRepoService.updateResource(resourceNodeResult);
			if(update!=1){
				throw new RuntimeException("更新资源Node失败，资源Node Id："+resourceNodeResult.getId());
			}
			return "success";
			//modifyUri(resourceNodeDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public void modifyUri(ResourceNodeDTO resourceNodeDTO) {
		flushCacheService.modify++;
		log.debug("resourceNodeDTO :" + resourceNodeDTO.getIsEnabled());
		ResourceNode resourceNode = resourceNodeRepoService.findById(resourceNodeDTO.getId());
		if (resourceNode == null) {
			throw new RuntimeException("没有相关资源节点");
		}

		Resource resource = resourceRepoService.findById(resourceNode.getResourceId());
		if (resource == null) {
			throw new RuntimeException("没有相关资源");
		}
		String uri = resource.getUri();
		if (!resourceNodeDTO.getUri().equals(resource.getUri())) {
			resource.setUri(resourceNodeDTO.getUri());
			int update = resourceRepoService.updateResource(resource);
			if(update!=1){
				throw new RuntimeException("更新资源失败，资源id："+resource.getId());
			}
			//判断是否要更新缓存
			if(!resourceNodeDTO.getUri().equals(uri)){
				List<Permission> permissions = permissionRepoService.findByResourceId(resource.getId());
				for (Permission permission : permissions) {
					String roleId = permission.getRoleId();
					memcachedUtilService.updateRoleToCacheByRoleId(roleId,"1");
				}
			}

			/*Resource entity = ResourceDTOConverter.toCreateResource(resourceNodeDTO);
				entity.setDn(new LdapName("uid=" + entity.getId() + "," + RESOURCE_BASE_DN));
				String resourceId = resourceRepoService.createResource(entity);
				resourceNode.setResourceId(resourceId);
				resourceRepoService.modifyResourceNode(resourceNode);*/
		}

	}

	@Override
	public void deleteResource(String resourceNodeId) {
		flushCacheService.modify++;
		ResourceNode resourceNode = resourceNodeRepoService.findById(resourceNodeId);
		if (resourceNode == null) {
			log.error("没有该资源节点: " + resourceNodeId);
			throw new RuntimeException("没有相关资源节点");
		}
		String resourceId = resourceNode.getResourceId();
		List<ResourceNode> resourceNodes1 = resourceNodeRepoService.findByResourceId(resourceId);
		//TODO delete resourceNode ? why delete resource
		if (resourceNodes1.size() < 2) {
			Resource resource = resourceRepoService.findById(resourceId);
			if (resource == null) {
				log.error("没有该资源:" + resourceId);
				throw new RuntimeException("没有该资源");
			}
			resourceRepoService.delete(resource.getId());
			List<Permission> permissions = permissionRepoService.findByResourceId(resource.getId());
			for (Permission permission : permissions) {
				String roleId = permission.getRoleId();
				permissionRepoService.deleteByResourceId(resource.getId());
				memcachedUtilService.updateRoleToCacheByRoleId(roleId,"1");
			}
		}
		resourceNodeRepoService.delete(resourceNode.getId());
	}

	@Override
	public void modifyEnable(String resourceNodeId, Boolean isEnabled) {
		flushCacheService.modify++;
		ResourceNode resourceNode = resourceNodeRepoService.findById(resourceNodeId);
		Resource resource = resourceRepoService.findById(resourceNode.getResourceId());
		if (resource == null) {
			throw new RuntimeException("没有该资源:" + resourceNode.getResourceId());
		}
		resource.setEnabled(isEnabled);
		resourceRepoService.updateResource(resource);
//		List<Permission> permissions = new ArrayList<>();
		List<Permission> permissionsList = permissionRepoService.findByResourceId(resource.getId());
		if(permissionsList!=null && permissionsList.size()>0){
			for (Permission permission : permissionsList) {
				Role role = roleRepoService.findById(permission.getRoleId());
				if (null!=role) {
					roleAdminService.registerPermission(role, resource);
//					permissions.add(permission);
				}
			}
		}
	}

	@Override
	public List<ResourceNodeDTO> getAllResources() {
		try {
			List<ResourceNode> resourceNodes = resourceNodeRepoService.findAll();
			List<ResourceNodeDTO> resourceNodeDTOs = new ArrayList<>(resourceNodes.size());
			for (ResourceNode resourceNode : resourceNodes) {
				//TODO use in to find resource
				ResourceNodeDTO result = ResourceNodeConverter.toResourceNodeDTO(resourceNode);
				/*Resource resource = resourceRepoService.findById(resourceNode.getResourceId());
				if (resource != null) {
					//throw new RuntimeException("没有该资源：" + resourceNode.getResourceId());
					result.setIsEnabled(resource.isEnabled());
					result.setUri(resource.getUri());
				}*/
				resourceNodeDTOs.add(result);
			}
			log.debug("所有资源：" + resourceNodeDTOs.size());
			/*
			 * Gson gson = new Gson(); String reslut =
			 * gson.toJson(resourceNodeDTOs);
			 */
			return resourceNodeDTOs;

		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	/*  *//**
	 * 根据父级标识查询子资源（权限）
	 *
	 * @param parentResourceId
	 * @return
	 *//*
			 * public List<ResourceNodeDTO> findByParentResourceId(String
			 * parentResourceId) { List<Resource> result =new ArrayList<>(); if
			 * (StringUtils.isEmpty(parentResourceId)) { result =
			 * resourceRepoService.findByLevel("1"); }
			 * resourceRepoService.findSubResourceNodeByParentResourceNodeId(
			 * parentResourceId); return null }
			 */

	/**
	 * 绑定用户到资源
	 *
	 * 在调用该方法前保证role和resource都是存在的
	 *
	 *//*
	private void registerPermission(Role role, Resource resource) {
		try {
			flushCacheService.modify++;
			Permission permission = permissionRepoService.findByResourceIdAndRoleId(resource.getId(), role.getId());
			if (permission == null) {
				permission = new Permission();
				if (role.isStatus() && resource.isEnabled()) {
					permission.setStatus(true);
				} else {
					permission.setStatus(false);
				}

				permission.setId(UUID.randomUUID().toString());
				permission.setResourceId(resource.getId());
				permission.setRoleId(role.getId());
				permission.setUri(resource.getUri());
				permissionRepoService.save(permission);
			} else {
				if (role.isStatus() && resource.isEnabled()) {
					permission.setStatus(true);
				} else {
					permission.setStatus(false);
				}
				permissionRepoService.save(permission);
			}

		} catch (Exception e) {
		}
	}*/

	@Override
	public List<ResourceNodeDTO> getSubResourcesByParentResourcesName(String parentResourceName) {
		try {
			List<ResourceNode> resourceNodes1 = resourceNodeRepoService.findByModuleNameLike(parentResourceName);
			if (resourceNodes1.isEmpty() || resourceNodes1.size() > 1) {
				return new ArrayList<>();
			}
			//TODO find all sub resource node use while
			List<ResourceNode> resourceNodes = resourceNodeRepoService.findByParentResourceId(resourceNodes1.get(0).getResourceId());
			List<ResourceNodeDTO> resourceNodeDTOs = new ArrayList<>(resourceNodes.size());
			for (ResourceNode resourceNode : resourceNodes) {
				//TODO use in to find resource 
				ResourceNodeDTO result = ResourceNodeConverter.toResourceNodeDTO(resourceNode);
				Resource resource = resourceRepoService.findById(resourceNode.getResourceId());
				if (resource == null) {
					throw new RuntimeException("没有该资源：" + resourceNode.getResourceId());
				}
				result.setIsEnabled(resource.isEnabled());
				result.setUri(resource.getUri());
				resourceNodeDTOs.add(result);
			}
			log.debug("所有资源：" + resourceNodeDTOs.size());
			return resourceNodeDTOs;

		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
}
