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
        //TODO use while
        List<Organization> organizations = organizationRepoService.findByParentId(parentId);
//        log.debug("根据父级机构ID查询结果:" + organizations.size() + organizations.get(0).id.toString())
        List<OrganizationDTO> result=new ArrayList<OrganizationDTO>();
       for (Organization organization : organizations) {
    	   log.debug("是否空指针:" + (organization == null));
    	   result.add(OrganizationConverter.toOrganizationDTO(organization));
	   }
       for (OrganizationDTO orgDTO : result) {
    	   //TODO use while
    	   List<Organization> organizations1 = organizationRepoService.findByParentId(orgDTO.getId());
		   if (organizations1.size() > 0) {
                orgDTO.setIsLeaf(false);
            } else {
                orgDTO.setIsLeaf( true);
            }
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
		//TODO 只查询一级的
		List<Organization> organizations = organizationRepoService.findByParentId(parentId);
//        log.debug("根据父级机构ID查询结果:" + organizations.size() + organizations.get(0).id.toString())
		List<OrganizationDTO> result=new ArrayList<OrganizationDTO>();
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
			 List<UserDTO> users = new ArrayList<UserDTO>();
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
    public void moveOrganization(String organizationId, String parentOrganizationId) {
    	log.debug("当前部门的Id:${organizationId},移动到的部门Id: ${parentOrganizationId}");
		if (organizationId.equals(parentOrganizationId)) {
		    return;
		}

		Organization organization = organizationRepoService.findById(organizationId);
		if (organization == null) {
		    throw new RuntimeException("---没有该机构:" + organizationId);
		}
		Organization descOrganization = organizationRepoService.findById(parentOrganizationId);
		if (descOrganization == null) {
			throw new RuntimeException("===没有该机构:" + parentOrganizationId);
		}

		organization.setParentId( parentOrganizationId);
		organizationRepoService.update(organization);
		//organizationRepoService.moveOrganization(organization.getId(), parentOrganizationId);
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
    	List<Organization> organizations = organizationRepoService.findAll();
		List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>(organizations.size());
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
		    throw new RuntimeException("机构名称不能重复" + organization.getName());
		}

		log.debug("==================:Organization.name Entity:" + organization.getName());

		if (StringUtils.isNotEmpty(entity.getParentId()) && !"0".equals(entity.getParentId()) && !"1".equals(entity.getParentId())) {
		    log.debug("查询父级机构" + entity.getParentId());

		    Organization organization1 = organizationRepoService.findById(entity.getParentId());
		    if (organization1 == null) {
		        throw new RuntimeException("父类机构不存在");
		    }

		} else {
			//TODO 这里做什么
		    //entity.setId( dn);
		}
		String[] leaderIds=entity.getLeaderIds().split(",");
		for (String leadId : leaderIds) {
			log.debug("查询负责人：" + leadId);
		    if (StringUtils.isNotEmpty(leadId) && "null".equals(leadId)) {
		        User user = userRepoService.findById(leadId);
		        if (user == null) {
		            throw new RuntimeException("用户不存在:" + leadId);
		        }
		    }
		}

		List<Organization> organziations = organizationRepoService.findByCode(entity.getCode());
		if (!organziations.isEmpty()) {
		    throw new RuntimeException("部门编号不能重复！");
		}
		int insert = organizationRepoService.save(entity);
		if(insert!=1){
			throw new RuntimeException("创建Organization异常，返回值：" +insert);
		}
		return entity.getId();
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
        /*for (UserDTO user : result.getOrganizationLeaders()) {
        	User u = userRepoService.findById(user.getId());
            if (u == null) {
                log.warn("没有该用户：" + user.getId());
            } else {
                user=UserConverter.toUserDTO(u);
            }
		}*/
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
/*
        log.debug("orgName:" + orgName + "; leaderName:" + leaderName);
        if (StringUtils.isEmpty(orgName) && StringUtils.isEmpty(leaderName)) { // 两者都为空时候没有任何筛选条件
            log.debug("查询条件两者都为空");
            Name base = new LdapName(BASE_DN);
            List<Organization> organizations = organizationRepoService.getAllOrganizations(base);
            log.debug("查询条件两者都为空：" + organizations.size());
            List<OrganizationDTO> result=new ArrayList<>();
            for (Organization org : organizations) {
            	 log.debug("==========org.name" + org.getName());
            	result.add(OrganizationConverter.toOrganizationDTO(org));
			}*/

/*
            List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>();

            for (OrganizationDTO organizationDTO : result) {


                List<UserDTO> userDTOList = new ArrayList<UserDTO>();
                for (UserDTO userDTO : organizationDTO.getOrganizationLeaders()) {
                	List<User> user = userRepoService.findUserByUUID(userDTO.getId());
                    if (null == user) {
                        throw new RuntimeException("没有该人员:" + userDTO.getId());
                    } else {
                    	
                        use(UserConverter) {
                            log.debug("user.get(0)" + UserConverter.toUserDTO(user).realName);
                            userDTO = user.get(0).toUserDTO();
                            log.debug("添加前：" + userDTO.realName);
                            userDTOList.add(userDTO);
                        }
                    }
                }
                organizationDTO.organizationLeaders = userDTOList
                organizationDTOs.add(organizationDTO)
            }

            def pageResult = PageListUtil.convert(paginator, organizationDTOs)
            return pageResult
        } else if (StringUtils.isNotEmpty(orgName) && StringUtils.isNotEmpty(leaderName)) { // 两者都不为空时候，同时满足两个筛选条件
            List<User> users = userRepoService.findLikeRealname(leaderName)
            Set<Organization> organizations = new HashSet<Organization>()
            log.debug("users.size" + users.size())
            def likeOrgNameresult = organizationRepoService.findLikeOrganizationName(orgName)
            organizations.addAll(likeOrgNameresult)
            def result = organizations.collect { org ->
                log.debug("==========org.name" + org.name)
                use(OrganizationConverter) {
                    org.toOrganizationDTO()
                }
            }

            List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>();

            for (OrganizationDTO organizationDTO : result) {
                List<UserDTO> userDTOList = new ArrayList<UserDTO>()
                for (UserDTO userDTO : organizationDTO.organizationLeaders) {
                    def user = userRepoService.findUserByUUID(userDTO.id)
                    if (null == user) {
                        throw new RuntimeException("没有该人员:" + user.uuid)
                    } else {
                        use(UserConverter) {
                            log.debug("user.get(0)" + user.get(0).toUserDTO().realName)
                            userDTO = user.get(0).toUserDTO();
                            log.debug("添加前：" + userDTO.realName)
                            userDTOList.add(userDTO)
                        }
                    }
                }
                organizationDTO.organizationLeaders = userDTOList
                organizationDTOs.add(organizationDTO)
            }

            def pageResult = PageListUtil.convert(paginator, organizationDTOs)
            return pageResult
        } else if (StringUtils.isEmpty(orgName) && StringUtils.isNotEmpty(leaderName)) { // 筛选负责人
            List<User> users = userRepoService.findLikeRealname(leaderName)
            log.debug("查询出：" + users.size())
            List<Organization> organizations = new ArrayList<Organization>()
            users.each { user ->
                def result = organizationRepoService.findByLeaderID(user.uuid)
                organizations.addAll(result)
            }

            def result = organizations.collect { org ->
                log.debug("==========org.name" + org.name)
                use(OrganizationConverter) {
                    org.toOrganizationDTO()
                }
            }

            List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>();

            for (OrganizationDTO organizationDTO : result) {
                List<UserDTO> userDTOList = new ArrayList<UserDTO>()
                for (UserDTO userDTO : organizationDTO.organizationLeaders) {
                    def user = userRepoService.findUserByUUID(userDTO.id)
                    if (null == user) {
                        throw new RuntimeException("没有该人员:" + user.uuid)
                    } else {
                        use(UserConverter) {
                            log.debug("user.get(0)" + user.get(0).toUserDTO().realName)
                            userDTO = user.get(0).toUserDTO();
                            log.debug("添加前：" + userDTO.realName)
                            userDTOList.add(userDTO)
                        }
                    }
                }
                organizationDTO.organizationLeaders = userDTOList
                organizationDTOs.add(organizationDTO)
            }

            def pageResult = PageListUtil.convert(paginator, organizationDTOs)
            return pageResult
        } else if (StringUtils.isNotEmpty(orgName) && StringUtils.isEmpty(leaderName)) { // 筛选部门
            log.debug("orgName:" + orgName + "; leaderName:" + leaderName)
            List<Organization> organizations = organizationRepoService.findLikeOrganizationName(orgName)
            def result = organizations.collect { org ->
                log.debug("==========org.name" + org.name)
                use(OrganizationConverter) {
                    org.toOrganizationDTO()
                }
            }

            List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>();

            for (OrganizationDTO organizationDTO : result) {
                List<UserDTO> userDTOList = new ArrayList<UserDTO>()
                for (UserDTO userDTO : organizationDTO.organizationLeaders) {
                    def user = userRepoService.findUserByUUID(userDTO.id)
                    if (null == user) {
                        throw new RuntimeException("没有该人员:" + user.uuid)
                    } else {
                        use(UserConverter) {
                            log.debug("user.get(0)" + user.get(0).toUserDTO().realName)
                            userDTO = user.get(0).toUserDTO();
                            log.debug("添加前：" + userDTO.realName)
                            userDTOList.add(userDTO)
                        }
                    }
                }
                organizationDTO.organizationLeaders = userDTOList
                organizationDTOs.add(organizationDTO)
            }

            def pageResult = PageListUtil.convert(paginator, organizationDTOs)
            return pageResult
        } else {
            return new PagedList<OrganizationDTO>()
        }*/

        return null;
    }

    public OrganizationDTO findOrganizationByCode(String code) {
        List<Organization> organizations = organizationRepoService.findByCode(code);
        if (organizations.isEmpty()) {
            return null;
        }
        Organization organization = organizations.get(0);
        OrganizationDTO result = OrganizationConverter.toOrganizationDTO(organization);
        return result;
    }


}