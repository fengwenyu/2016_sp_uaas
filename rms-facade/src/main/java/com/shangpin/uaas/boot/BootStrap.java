package com.shangpin.uaas.boot;

import com.shangpin.uaas.api.admin.org.OrganizationAdminFacade;
import com.shangpin.uaas.api.admin.org.OrganizationDTO;
import com.shangpin.uaas.api.admin.role.RoleAdminFacade;
import com.shangpin.uaas.api.admin.role.RoleDTO;
import com.shangpin.uaas.api.admin.user.Status;
import com.shangpin.uaas.api.admin.user.UserAdminFacade;
import com.shangpin.uaas.api.admin.user.UserDTO;
import com.shangpin.uaas.services.api.MemcachedUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class BootStrap {
    protected static Logger log = LoggerFactory.getLogger(BootStrap.class);
	private static final String UAAS = "UAAS";
	private static final String TMSROLE = "tmsrole";
	@Autowired
	private UserAdminFacade userAdminFacadeService;
	@Autowired
    private RoleAdminFacade roleAdminFacadeService;
	@Autowired
    private OrganizationAdminFacade organizationAdminFacadeService;

	@Autowired
    private MemcachedUtilService memcachedUtilService;


    @PostConstruct
    public void init() throws Exception{
        //初始化用户，角色
        UserDTO userDTO = userAdminFacadeService.getUserByUsername("admin");
        String userUUID = null;
        String roleUUID = null;
        if(userDTO == null){
            userDTO = new UserDTO();
            userDTO.setRealName("TMS管理员");
            userDTO.setUserCode("admin");
            userDTO.setUsername("admin");
            userDTO.setPassword("111111");
            userDTO.setStatus(Status.ENABLED);
            OrganizationDTO orgDTO = organizationAdminFacadeService.findOrganizationByCode(UAAS);
            if(orgDTO == null){
                orgDTO = new OrganizationDTO();
                orgDTO.setIsEnabled(true);
                orgDTO.setDescription("系统机构");
                orgDTO.setName(UAAS);orgDTO.setCode(UAAS);
                orgDTO.setId( organizationAdminFacadeService.createOrganization(orgDTO));
            }
            userDTO.setOrganizationId (orgDTO.getId());
            userUUID = userAdminFacadeService.createUser(userDTO);
        }
        RoleDTO roleDTO = roleAdminFacadeService.getRoleByCode(TMSROLE);
        if(roleDTO == null){
            roleDTO = new RoleDTO();
            roleDTO.setDescription("权限系统管理员角色");
            roleDTO.setCode(TMSROLE);
            roleDTO.setIsEnabled(true);
            roleUUID = roleAdminFacadeService.createRole(roleDTO);
        }
        if(userUUID != null || roleUUID != null){
            userUUID = userUUID == null ? userDTO.getId() : userUUID;
            roleUUID = roleUUID == null ? roleDTO.getId() : roleUUID;
            roleAdminFacadeService.assignRoleToUser(roleUUID,userUUID);
        }
        //初始化权限缓存
        long start = System.currentTimeMillis();
        boolean result = memcachedUtilService.storAllRoleToCache();
        if(result){
            log.info("缓存权限完成，用时:"+(System.currentTimeMillis()-start)+"毫秒");
        }else{
            log.info("缓存权限失败，用时:"+(System.currentTimeMillis()-start)+"毫秒");
            throw new Exception("缓存权限失败");
        }
    }
    @PreDestroy
    public void destroy() {
    	
    }
	
	
}
