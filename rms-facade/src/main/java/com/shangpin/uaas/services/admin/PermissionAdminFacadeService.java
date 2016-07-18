package com.shangpin.uaas.services.admin;

import com.shangpin.uaas.api.admin.permission.PermissionAdminFacade;
import com.shangpin.uaas.api.admin.permission.PermissionDTO;
import com.shangpin.uaas.api.admin.resource.ResourceNodeDTO;
import com.shangpin.uaas.api.admin.resource.ResourceType;
import com.shangpin.uaas.api.admin.role.RoleDTO;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;
import com.shangpin.uaas.convert.PermissionConverter;
import com.shangpin.uaas.convert.ResourceNodeConverter;
import com.shangpin.uaas.convert.RoleConverter;
import com.shangpin.uaas.entity.Permission;
import com.shangpin.uaas.entity.Resource;
import com.shangpin.uaas.entity.ResourceNode;
import com.shangpin.uaas.entity.Role;
import com.shangpin.uaas.services.dao.PermissionRepoService;
import com.shangpin.uaas.services.dao.ResourceNodeRepoService;
import com.shangpin.uaas.services.dao.ResourceRepoService;
import com.shangpin.uaas.services.dao.RoleRepoService;
import com.shangpin.uaas.util.PageListUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PermissionAdminFacadeService implements PermissionAdminFacade {
    protected static Logger log = LoggerFactory.getLogger(FlushCacheService.class);

    @Autowired
    PermissionRepoService permissionRepoService;
    @Autowired
    RoleRepoService roleRepoService;
    @Autowired
    ResourceRepoService resourceRepoService;
    @Autowired
    ResourceNodeRepoService resourceNodeRepoService;
    @Autowired
    FlushCacheService flushCacheService;
    public synchronized void bindPermissionWithRoles(String resourceNodeId, List<String> roleIds) {

        ResourceNode resourceNode = resourceNodeRepoService.findById(resourceNodeId);
        if (resourceNode == null) {
            throw new RuntimeException("该资源节点已经被删除：" + resourceNodeId);
        }
        Resource resource = resourceRepoService.findById(resourceNode.getResourceId());
        if (resource == null) {
            throw new RuntimeException("该资源已经被删除：" + resourceNode.getResourceId());
        }
        List<Permission> permissions = permissionRepoService.findByResourceId(resource.getId());
        for (Permission permission : permissions) {
            permissionRepoService.delete(permission.getId());
        }
        List<Role> roles = new ArrayList<>();
        for (String roleId : roleIds) {
            Role tempRoles = roleRepoService.findById(roleId);
            if (tempRoles == null) {
                log.error("该角色已经被删除" + roleId);
            }else{
                roles.add(tempRoles);
            }

        }
        for (Role role : roles) {
            registerPermission(role, resource);
        }
        flushCacheService.modify++;
    }

    @Transactional
    public synchronized void bindRoleWithPermissions(String roleId, List<String> resourceNodeIds) {

        if (StringUtils.isEmpty(roleId)) {
            throw new RuntimeException("角色不能为空");
        }

        Role role = roleRepoService.findById(roleId);
        if (role == null) {
            throw new RuntimeException("没有该角色:" + roleId);
        }
        //删除原来配置的权限
        permissionRepoService.deleteByRoleId(roleId);
        log.debug("查询出的资源数为：" + resourceNodeIds.size());

        Set<String> singleIds = new HashSet<>();
        List<ResourceNode> resourceNodes=resourceNodeRepoService.findAllInNodeIds(resourceNodeIds);
        if(CollectionUtils.isEmpty(resourceNodes) || resourceNodeIds.size()!=resourceNodes.size()){
            throw new RuntimeException("部分资源节点不存在！");
        }
        for (ResourceNode resourceNode2 : resourceNodes) {
            singleIds.add(resourceNode2.getResourceId());
        }
        List<String> ids = new ArrayList<>(singleIds);
        List<Resource> resources = resourceRepoService.findByIdIn(ids);
        for (Resource resource : resources) {
            registerPermission(role, resource);
        }
        flushCacheService.modify++;
    }

    @Override
    public PagedList<PermissionDTO> findAllPermissionsByRoles(List<String> roleIds, Paginator paginator) {
        if (roleIds.isEmpty()) {
            return PageListUtil.convert(paginator, new ArrayList<PermissionDTO>());
        }
        List<Permission> permissions = permissionRepoService.findByRoleIdIn(roleIds);

        Map<String, Permission> permissionMap = new HashMap<>();
        for (Permission permission : permissions) {
            if (!permissionMap.containsKey(permission.getId())) {
                permissionMap.put(permission.getId(), permission);
            }
        }
        //TODO is here has repeat permission?
        Collection<Permission> permission= permissionMap.values();
        List<PermissionDTO> resultDTOs=new ArrayList<>(permission.size());
        for (Permission permissio : permission) {
            resultDTOs.add(PermissionConverter.toPermissionDTO(permissio));
        }

        return PageListUtil.convert(paginator, resultDTOs);
    }

    public PagedList<ResourceNodeDTO> findAllResourceNodesByRoles(List<String> roleIds, String resourceType, Paginator paginator) {

        List<Permission> permissions = permissionRepoService.findByRoleIdIn(roleIds);
        if(permissions==null || permissions.isEmpty()){
            return PageListUtil.convert(paginator, new ArrayList<ResourceNodeDTO>());
        }
        Set<String> resourceIds=new HashSet<>(permissions.size());
        for (Permission permission : permissions) {
            resourceIds.add(permission.getResourceId());
        }

        List<String> reNodeIds = new ArrayList<>(resourceIds);
        if(reNodeIds.isEmpty()){
            return PageListUtil.convert(paginator, new ArrayList<ResourceNodeDTO>());
        }

        Map<String, ResourceNode> allResourceNodes = new HashMap<>();
        List<ResourceNode> resourceNodes = resourceNodeRepoService.findByResourceIdIn(reNodeIds);
        for (ResourceNode resourceNode : resourceNodes) {
            if (ResourceType.BUTTON.name().equals(resourceType)) {
                allResourceNodes.put(resourceNode.getId(), resourceNode);
            }
        }
        Collection<ResourceNode> setResourceNodes = allResourceNodes.values();
        List<ResourceNodeDTO> resultResourceNodes = new ArrayList<>();
        for (ResourceNode resourceNode : setResourceNodes) {
            resultResourceNodes.add(ResourceNodeConverter.toResourceNodeDTO(resourceNode));
        }
        return PageListUtil.convert(paginator, resultResourceNodes);
    }

    @Override
    public List<RoleDTO> findAllRolesByResourceNode(String resourceNodeId) {
        ResourceNode resourceNode = resourceNodeRepoService.findById(resourceNodeId);
        if (resourceNode == null) {
            log.debug("没有该资源Node:" + resourceNodeId);
            throw new RuntimeException("没有该资源Node:" + resourceNodeId);
        }
        String resourceId = resourceNode.getResourceId();
        List<Permission> permissions = permissionRepoService.findByResourceId(resourceId);
        if(permissions==null ||permissions.isEmpty()){
            return new ArrayList<>();
        }
        Set<String> roleIds = new HashSet<>();
        for (Permission permission : permissions) {
            roleIds.add(permission.getRoleId());
        }
        List roIds = new ArrayList<>(roleIds);
        List<Role> roles = roleRepoService.findAllIn(roIds);
        List<RoleDTO> result=RoleConverter.toRoleDTOList(roles);
        return result;
    }

    /**
     * 绑定用户到资源
     *
     * 在调用该方法前保证role和resource都是存在的
     *
     * @param role
     * @param resource
     */
    private void registerPermission(Role role, Resource resource) {
        List<Permission> permissions = permissionRepoService.findByResourceIdAndRoleId(resource.getId(), role.getId());
        Permission permission;
        if(permissions==null || permissions.isEmpty()){
            permission = null;
        }else{
            permission = permissions.get(0);
        }

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
            permissionRepoService.insert(permission);
        } else {
            if (role.isStatus() && resource.isEnabled()) {
                permission.setStatus(true);
            } else {
                permission.setStatus(false);
            }
            permissionRepoService.insert(permission);
        }
    }

}
