package com.shangpin.uaas.services.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.shangpin.uaas.api.admin.org.OrganizationAdminFacade;
import com.shangpin.uaas.api.admin.org.OrganizationCriteriaDTO;
import com.shangpin.uaas.api.admin.org.OrganizationDTO;
import com.shangpin.uaas.api.admin.user.UserDTO;
import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;
import com.shangpin.uaas.convert.OrganizationConverter;
import com.shangpin.uaas.convert.OrganizationDTOConverter;
import com.shangpin.uaas.convert.UserConverter;
import com.shangpin.uaas.entity.Organization;
import com.shangpin.uaas.entity.User;
import com.shangpin.uaas.services.dao.OrganizationRepoService;
import com.shangpin.uaas.services.dao.UserRepoService;
import com.shangpin.uaas.util.PageListUtil;

/**
 * 机构服务
 *
 */
@Service
public class OrganizationAdminFacadeService implements OrganizationAdminFacade {
	protected static Logger log = LoggerFactory.getLogger(FlushCacheService.class);

    @Autowired
    OrganizationRepoService organizationRepoService;
    @Autowired
    UserRepoService userRepoService;

    @Override
    public PagedList<OrganizationDTO> findAllSubOrganizationsByParentId(String parentId, Paginator paginator) {
        if (StringUtils.isEmpty(parentId) || "0".equals(parentId)) {
            parentId = "1";
        }
        List<Organization> organizations = organizationRepoService.findByParentId(parentId);
        log.debug("根据父级机构ID查询结果:" + organizations.size() + organizations.get(0).getId());
        List<OrganizationDTO> result=new ArrayList<>();
       for (Organization organization : organizations) {
    	   log.debug("是否空指针:" + (organization == null));
    	   result.add(OrganizationConverter.toOrganizationDTO(organization));
	   }
       for (OrganizationDTO orgDTO : result) {
    	   List<Organization> organizations1 = organizationRepoService.findByParentId(orgDTO.getId());
		   orgDTO.setIsLeaf(!(organizations1.size()>0));
		   for (UserDTO user : orgDTO.getOrganizationLeaders()) {
			   if (StringUtils.isEmpty(user.getId())) {
                   User  u = userRepoService.findById(user.getId());
                   if (u == null) {
                       log.error("没有该人员：" + user.getId());
                   } else {
                       //User u = us.get(0);
                       user=UserConverter.toUserDTO(u);
                   }
               }
		   }
	   }
       PagedList<OrganizationDTO> finalResult = PageListUtil.convert(paginator, result);
        log.debug("结果集：" + finalResult.getCurrentPage() + ";" + finalResult.getPageSize() + ";" + finalResult.getTotalCount());
        return PageListUtil.convert(paginator, result);
    }

    @Override
    public List<OrganizationDTO> findOneLevelOrganizationsByParentId(String parentId) {
    	if (StringUtils.isEmpty(parentId) || "0".equals(parentId)) {
		    parentId = "1";
		}
		List<Organization> organizations = organizationRepoService.findByParentId(parentId);
//        log.debug("根据父级机构ID查询结果:" + organizations.size() + organizations.get(0).id.toString())
		List<OrganizationDTO> result=new ArrayList<>();
		for (Organization organization : organizations) {
			log.debug("是否空指针:" + (organization == null));
			  result.add(OrganizationConverter.toOrganizationDTO(organization));
		}
		log.debug("**********************organizations.size():" + organizations.size());
		for (OrganizationDTO orgDTO : result) {
			 List<Organization> organizations1 = organizationRepoService.findByParentId(orgDTO.getId());
			 if (organizations1.size() > 0) {
		         orgDTO.setIsLeaf(false);
		     } else {
		         orgDTO.setIsLeaf( true);
		     }
			 List<UserDTO> users = new ArrayList<>();
			 for (UserDTO user : orgDTO.getOrganizationLeaders()) {
				 if (StringUtils.isNotEmpty(user.getId())) {
		             log.debug("**********************:" + user.getId());
		             User u = userRepoService.findById(user.getId());

		             if (u == null) {
		                 log.error("没有该人员：" + user.getId());
		             } else {
		                 log.debug("**********************:" + u.getRealName());
		                 user=UserConverter.toUserDTO(u);
						 users.add(user);
		             }
		         }
			}
		   orgDTO.setOrganizationLeaders(users);
		}
		
//        def finalResult = PageListUtil.convert(paginator, result)
//        log.debug("结果集：" + finalResult.currentPage + ";" + finalResult.pageSize + ";" + finalResult.totalCount)
		return result;

    }

    /**
     * 移动部门
     *
     * @param organizationId 要移动的部门 必填
     * @param parentOrganizationId 移到的目标部门 必填
     */
    @Override
    public String moveOrganization(String organizationId, String parentOrganizationId) {
    	log.debug("当前部门的Id:${organizationId},移动到的部门Id: ${parentOrganizationId}");
		if (organizationId.equals(parentOrganizationId)) {
		    return "success";
		}

		Organization organization = organizationRepoService.findById(organizationId);
		if (organization == null) {
			return "当前部门不存在！";
//		    throw new RuntimeException("---没有该机构:" + organizationId);
		}
		Organization descOrganization = organizationRepoService.findById(parentOrganizationId);
		if (descOrganization == null) {
			return "目标部门不存在！";
//			throw new RuntimeException("===没有该机构:" + parentOrganizationId);
		}

		organization.setParentId( parentOrganizationId);
		//判断父部门下有没有该名称的部门
		List<Organization> byNameAndParentId = organizationRepoService.findByNameAndParentId(organization.getName(), organization.getParentId());
		if(byNameAndParentId!=null&&byNameAndParentId.size()>0){
			for (Organization organization1 : byNameAndParentId) {
				if(!organization1.getId().equals(organization.getId())){
					return "该部门下已经有名字为 "+organization1.getName()+" 的部门了！";
				}
			}
		}
		int update = organizationRepoService.update(organization);
		if(update!=1){
			throw new RuntimeException("移动部门失败，部门id:" + organization.getId());
		}
		return "success";
		//organizationRepoService.moveOrganization(organization.getId(), parentOrganizationId);
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
    	List<Organization> organizations = organizationRepoService.findAll();
		List<OrganizationDTO> organizationDTOs = new ArrayList<>(organizations.size());
		for (Organization organization : organizations) {
			OrganizationDTO organizationDTO=OrganizationConverter.toOrganizationDTO(organization);
		organizationDTOs.add(organizationDTO);
      }
     return organizationDTOs;
    }

    @Override
    public  String createOrganization(OrganizationDTO organization) {
    	log.debug("==================:organization.name" + organization.getName());
		Organization entity=  OrganizationDTOConverter.toCreateEntity(organization);

		List<Organization> checkNameResult = organizationRepoService.findByName(organization.getName());
		if (!checkNameResult.isEmpty()) {
			return "部门名称"+organization.getName()+"重复";
		   // throw new RuntimeException("机构名称不能重复" + organization.getName());
		}

		log.debug("==================:Organization.name Entity:" + organization.getName());

		if (StringUtils.isNotBlank(entity.getParentId())) {
			if(!"0".equals(entity.getParentId())&& !"1".equals(entity.getParentId())){
				log.debug("查询父级机构" + entity.getParentId());
				Organization organization1 = organizationRepoService.findById(entity.getParentId());
				if (organization1 == null) {
//					throw new RuntimeException("父类机构不存在");
					return "\"父类机构不存在\"";
				}
			}
		}else{
//			throw new RuntimeException("该部门没有设置父节点,organizationId:"+entity.getId());
			return "该部门没有设置父节点";
		}
		String[] leaderIds=entity.getLeaderIds().split(",");
		for (String leadId : leaderIds) {
			log.debug("查询负责人：" + leadId);
		    if (StringUtils.isNotEmpty(leadId) && !"null".equals(leadId)) {
		        User user = userRepoService.findById(leadId);
		        if (user == null) {
//		            throw new RuntimeException("用户不存在:" + leadId);
					return "该负责人不存在";
		        }
		    }
		}

		List<Organization> organziations = organizationRepoService.findByCode(entity.getCode());
		if (!organziations.isEmpty()) {
			return "部门编号不能重复";
//		    throw new RuntimeException("部门编号不能重复！");
		}
		int insert = organizationRepoService.save(entity);
		if(insert!=1){
			throw new RuntimeException("创建部门出错，部门id：" +entity.getId());
		}
		return "success";
    }

    @Override
    public  void modifyOrganization(OrganizationDTO organization) {
    	Organization org = organizationRepoService.findById(organization.getId());
		if (org == null) {
		    throw new RuntimeException("没有该机构：" + organization.getId());
		}
		Organization entity = OrganizationDTOConverter.toUpdateEntity(org, organization);
		organizationRepoService.update(entity);
		log.debug("新名字：" + organization.getName());
		/*if (!organization.getName().equals(entity.getName())) {
		    organizationRepoService.rename(entity.getId().toString(), dn.toString());
		}*/
    }

    @Override
    public  void deleteOrganization(String id) {
    	//TODO valid not exists
    	organizationRepoService.delete(id);
    }

    @Override
    public OrganizationDTO getOrganization(String id) {
        log.debug("获取机构：" + id);
        Organization organization = organizationRepoService.findById(id);
        if (organization == null) {
            throw new RuntimeException("没有该机构：" + id);
        }
        OrganizationDTO result = OrganizationConverter.toOrganizationDTO(organization);
		List<Organization> byParentId = organizationRepoService.findByParentId(result.getId());
		String rn ="/" + organization.getName();
		String pId = organization.getParentId();
		while(pId!=null && !"1".equals(pId)&& !"0".equals(pId)){
			Organization organizations1 = organizationRepoService.findById(pId);
			if(organizations1 !=null ){
				rn=("/"+organizations1.getName())+rn;
				pId = organizations1.getParentId();
			}else{
				pId =null;
			}
		}
		result.setRdn(rn);
		if (byParentId.size() > 0) {
			result.setIsLeaf(false);
		} else {
			result.setIsLeaf(true);
		}
        log.debug("############" + result.getName());

        return result;
    }

    @Override
    public PagedList<OrganizationDTO> findAllOrganizationsByCriteria(OrganizationCriteriaDTO organizationCriteria, Paginator paginator) {
        String orgName = organizationCriteria.getOrganizationNameLike();
        String leaderName = organizationCriteria.getLeaderNameLike();

        if (orgName.contains("*") || orgName.contains("?")) {
            throw new RuntimeException("输入中带有非法字符：如*?");
        }
        if (leaderName.contains("*") || orgName.contains("?")) {
            throw new RuntimeException("输入中带有非法字符：如*?");
        }

        return null;
    }

    public OrganizationDTO findOrganizationByCode(String code) {
        List<Organization> organizations = organizationRepoService.findByCode(code);
        if (organizations.isEmpty()) {
            return null;
        }
        Organization organization = organizations.get(0);
        return OrganizationConverter.toOrganizationDTO(organization);
    }


}