package com.shangpin.uaas.controller.admin;

import com.shangpin.uaas.api.admin.user.Gender;
import com.shangpin.uaas.api.admin.user.Status;
import com.shangpin.uaas.api.admin.user.UserDTO;
import com.shangpin.uaas.api.facade.user.UserRoleDTO;
import com.shangpin.uaas.entity.OrganizationUnion;
import com.shangpin.uaas.entity.Role;
import com.shangpin.uaas.entity.User;
import com.shangpin.uaas.entity.UserRole;
import com.shangpin.uaas.services.admin.RoleAdminFacadeService;
import com.shangpin.uaas.services.admin.UserAdminFacadeService;
import com.shangpin.uaas.services.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
	protected static Logger log = LoggerFactory.getLogger(UserController.class);
	// exportService exportService;
	@Autowired
	UserRoleRepoService userRoleRepoService;
	@Autowired
	RoleAdminFacadeService roleAdminFacadeService;
	@Autowired
	UserAdminFacadeService userAdminFacadeService;
	@Autowired
	UserRepoService userRepoService;
	@Autowired
	OrganizationRepoService organizationRepoService;
	@Autowired
	RoleRepoService roleRepoService;
	@Autowired
	PermissionRepoService permissionRepoService;

	@RequestMapping("/login")
	@ResponseBody
	public Object index() {
		return "用户控制器";
	}

	/***
	 * 上传绑定
	 * @param userRole
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importUserWithRole")
	@ResponseBody
	public HashMap<String, Object> importUserWithRole(@RequestParam(value = "userRole", required = true) MultipartFile userRole, HttpServletRequest request,
													  HttpServletResponse response) throws Exception {
		HashMap<String, Object> menusMap = new HashMap<String, Object>();
		if (userRole.isEmpty()) {
			menusMap.put("msg", "file cannot be empty");
			return menusMap;
		}
		XSSFWorkbook hssfWorkbook = new XSSFWorkbook(userRole.getInputStream());
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// 循环行Row
			List<UserRoleDTO> rList = new ArrayList<UserRoleDTO>();
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				UserRoleDTO mDto = new UserRoleDTO();
				XSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				mDto.setUserId(hssfRow.getCell(0).getStringCellValue());
				mDto.setRoleId(hssfRow.getCell(1).getStringCellValue());
				rList.add(mDto);
			}
			batchUserRolesHandler(rList);
			menusMap.put("msg", "操作成功");
			menusMap.put("data", rList);
			return menusMap;
		}
		return null;
	}


	@RequestMapping("/batchUserRolesHandler")
	protected void batchUserRolesHandler(List<UserRoleDTO> userDTOList) {
		for (UserRoleDTO userRoleDTO : userDTOList) {
			String username = userRoleDTO.getUserId();
			String rolename = userRoleDTO.getRoleId();
			List<User> users = userRepoService.findByUsername(username);
			List<Role> roles = roleRepoService.findByName(rolename);
			boolean hasExist = false;
			if (!users.isEmpty() && !roles.isEmpty()) {
				User user = users.get(0);
				Role role = roles.get(0);
				List<UserRole> userRoles = userRoleRepoService.findByUserId(user.getId());
				for (UserRole userRole : userRoles) {
					if (userRole.getRoleId().equals(role.getId())) {
						hasExist = true;
					}
				}

				if (!hasExist) {
					roleAdminFacadeService.createUserRole(user, role);
				}
			}
		}
	}
	/***
	 * 用户批量导入
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public HashMap<String, Object> upload (@RequestParam(value = "user", required = true) MultipartFile user, HttpServletRequest request)
			throws Exception{
		HashMap<String, Object> menusMap = new HashMap<String, Object>();
		if (user.isEmpty()) {
			menusMap.put("msg", "file cannot be empty");
			return menusMap;
		}
		log.info("========================上传文件不为空=========================");

		XSSFWorkbook hssfWorkbook = new XSSFWorkbook(user.getInputStream());
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// 循环行Row
			List<UserDTO> rList = new ArrayList<UserDTO>();
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				UserDTO mDto = new UserDTO();
				XSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				DecimalFormat df=new DecimalFormat("0");
				mDto.setRealName(hssfRow.getCell(0).toString());
				mDto.setUsername(hssfRow.getCell(1).toString());
				Gender ec=null;
				if (hssfRow.getCell(2).toString().equals("FEMALE")) {
					ec=Gender.FEMALE;
				}else if (hssfRow.getCell(2).toString().equals("MALE")) {
					ec=Gender.MALE;
				}
				mDto.setGender(ec);
				try {
					mDto.setUserCode(hssfRow.getCell(3)==null ? null :df.format(hssfRow.getCell(3).getNumericCellValue()));
				} catch (Exception e) {
					mDto.setUserCode(hssfRow.getCell(3).getStringCellValue());
				}
				mDto.setOrganizationId(hssfRow.getCell(4).toString());
				try {
					mDto.setMobile(hssfRow.getCell(5)==null ? null :df.format(hssfRow.getCell(5).getNumericCellValue()));
				} catch (Exception e) {
					mDto.setMobile(hssfRow.getCell(5).getStringCellValue());
				}
				try {
					mDto.setTelephone(hssfRow.getCell(6)==null ? null:df.format(hssfRow.getCell(6).getNumericCellValue()));
				} catch (Exception e) {
					mDto.setTelephone(hssfRow.getCell(6).getStringCellValue());
				}

				mDto.setDirectLeaderId(hssfRow.getCell(7)==null ? null :hssfRow.getCell(7).toString());
				mDto.setEmail(hssfRow.getCell(8)==null ? null :hssfRow.getCell(8).toString());
				mDto.setJobLevel(df.format(hssfRow.getCell(9).getNumericCellValue()));
				mDto.setPassword(hssfRow.getCell(10).toString());
				mDto.setBirth(hssfRow.getCell(11)==null ? null :hssfRow.getCell(11).toString());
				mDto.setWorkPlace(hssfRow.getCell(12).toString());
				/*Status statu=null;
				if (hssfRow.getCell(13).toString().equals("ENABLED")) {
					statu=Status.ENABLED;
				}else if (hssfRow.getCell(13).toString().equals("DISABLED")) {
					statu=Status.DISABLED;
				}*/
				mDto.setStatus("ENABLED".equals(hssfRow.getCell(13).toString())? Status.ENABLED:Status.DISABLED);
				rList.add(mDto);
			}
			List<String>  result=batchUsersHandler(rList);
			menusMap.put("msg", "操作成功");
			menusMap.put("data", result);
			return menusMap;
		}
		return menusMap;
	}

	/**
	 * 批量保存用户列表
	 * @param userDTOList
	 */
	private List<String>  batchUsersHandler(List<UserDTO> userDTOList) {
		StringBuilder sb = new StringBuilder(3);
		Iterator<UserDTO> it = userDTOList.iterator();

		List<String> result = new ArrayList<String>();
		while (it.hasNext()) {
			UserDTO userDTO = it.next();
			//这里需要对user属性进行验证
			if (StringUtils.isEmpty(userDTO.getUsername())) {
				continue;
			}
			if (StringUtils.isEmpty(userDTO.getUserCode())) {
				continue;
			}
			if (StringUtils.isEmpty(userDTO.getRealName())) {
				continue;
			}
			if (StringUtils.isEmpty(userDTO.getPassword())) {
				continue;
			}
			//校验所在部门
			if (StringUtils.isEmpty(userDTO.getOrganizationId())) {
				continue;
			}
			//校验职位类别
			if (StringUtils.isEmpty(userDTO.getJobLevel())) {
				continue;
			}
			//校验办公区
			if (StringUtils.isEmpty(userDTO.getWorkPlace())) {
				continue;
			}
			try {
				//校验用户编码是否存在
				UserDTO userDTO1 = userAdminFacadeService.getUserByUserCode(userDTO.getUserCode());
				if (userDTO1 != null) {
					continue;
				}
				//校验用户名称是否存在
				userDTO1 = userAdminFacadeService.getUserByUsername(userDTO.getUsername());
				if (userDTO1 != null) {
					continue;
				}

				//校验部门机构
				String[] orgs = userDTO.getOrganizationId().split("/");
				sb.delete(0, sb.length());
				for (String string : orgs) {
					if (!StringUtils.isEmpty(string)) {
						sb.append(string).append("|");
					}
				}
				List<OrganizationUnion> byUnionName = organizationRepoService.findByUnionName(sb.toString(), "\\|");
				if (byUnionName == null || byUnionName.isEmpty()) {
					log.info("=========${userDTO.username}=失败，部门不存在==========");
					result.add(userDTO.getUsername() + "失败，部门不存在");
					continue;
				}
				OrganizationUnion organizationUnion = byUnionName.get(0);
				userDTO.setOrganizationId(organizationUnion.getId());
				//校验直属领导
				if (!StringUtils.isEmpty(userDTO.getDirectLeaderId())) {
					UserDTO userDTO2 = userAdminFacadeService.getUserByUsername(userDTO.getDirectLeaderId());
					if (userDTO2 == null) {
						log.info("=========${userDTO.username}=失败，用户已存在==========");
						result.add(userDTO.getUsername() + "失败，用户已存在");
						continue;
					}
					userDTO.setDirectLeaderId(userDTO2.getId());
				}
				String userId = userAdminFacadeService.createUser(userDTO);
				if (!userId.isEmpty()) {
					log.info("=========${userDTO.username}=成功==========");
					result.add(userDTO.getUsername() + "成功");
				} else {
					log.info("=========${userDTO.username}=失败==========");
					result.add(userDTO.getUsername() + "失败");
				}

			} catch (RuntimeException e) {
				e.getMessage();
			}

		}
		return result;

	}


/*	public void exportUserWithRole() {
		UserCriteriaDTO userCriteriaDTO = new UserCriteriaDTO();
		// TODO 有问题的 不能直接走Repository
		List<User> result = userRepoService.findAll(UserCriteriaConvert.toUser(userCriteriaDTO));
		for (User user : result) {
			List<UserRole> userRoles = userRoleRepoService.findByUserId(user.getId());
			Set<String> roleIds = new HashSet<>(userRoles.size());
			for (UserRole userRole : userRoles) {
				roleIds.add(userRole.getRoleId());
			}
			List<Role> role = roleRepoService.findAll(roleIds);

		}
	}*/

	/*
	 * public void exportUser () { String format = "excel"; UserCriteriaDTO
	 * userCriteriaDTO = new UserCriteriaDTO(); Paginator paginator = new
	 * Paginator(); paginator.setPage(1); paginator.setPageSize(1000);
	 * PagedList<UserDTO> userDTOPagedList =
	 * userAdminFacadeService.findAllUsersByCriteria(userCriteriaDTO,
	 * paginator); List<UserDTO> userDTO = userDTOPagedList.getList();
	 * response.contentType = grailsApplication.config.grails.mime.types[format]
	 * response.setHeader("Content-disposition",
	 * "attachment; filename=users.xls")
	 * 
	 * 
	 * exportService.export(format, response.outputStream,userList, [:], [:]) }
	 */
	/*
	 * def importUserWithRole () {
	 * 
	 * def f = ((MultipartHttpServletRequest)request).getFile('userRole') if
	 * (f.empty) { flash.message = 'file cannot be empty' render "该文件是空文件"
	 * return }
	 * 
	 * CommonExcelImport<UserRoleDTO> commonExcelImport = new
	 * CommonExcelImport<UserRoleDTO>(f.inputStream ,
	 * USER_ROLE_COLUMN_PROPERTIES) def userDTOList =
	 * commonExcelImport.getModelList(UserRoleDTO.class)
	 * 
	 * batchUserRolesHandler(userDTOList)
	 * 
	 * render (contentType: "application/json"){ msg = "操作成功" data = userDTOList
	 * }
	 * 
	 * }
	 */

	/*
	 * protected batchUserRolesHandler (List<UserRoleDTO> userDTOList) {
	 * userDTOList.each { userRoleDTO -> String username = userRoleDTO.userId
	 * String rolename = userRoleDTO.roleId List<User> users =
	 * userRepoService.findByUsername(username) List<Role> roles =
	 * roleRepoService.findByRoleCode(rolename) boolean hasExist = false if
	 * (!users.isEmpty() && !roles.isEmpty()) { User user = users.get(0) Role
	 * role = roles.get(0) List<UserRole> userRoles
	 * =userRoleRepoService.findUserRoleByUser(user.uuid) userRoles.each {
	 * userRole -> if (userRole.roleId.equals(role.uuid)) { hasExist = true } }
	 * 
	 * if (!hasExist) { roleAdminFacadeService.createUserRole(user,role) } } } }
	 * public Map<String, Object> upload (@RequestParam("imgFile")
	 * CommonsMultipartFile file, HttpServletRequest httpServletRequest,
	 * HttpServletRequest request) { Map<String, Object> map=new HashMap<>(); if
	 * (file.isEmpty()) { map.put("msg", "file cannot be empty"); return null; }
	 * log.info("========================上传文件不为空=========================");
	 * 
	 * CommonExcelImport<UserDTO> commonExcelImport = new
	 * CommonExcelImport<UserDTO>(file.getInputStream() ,
	 * USER_CLOUMN_PROPERTIES); def userDTOList =
	 * commonExcelImport.getModelList(UserDTO.class);
	 * 
	 * def result = batchUsersHandler(userDTOList);
	 * log.info("=================上传用户结果：${result}====================");
	 * map.put("msg", "操作成功"); map.put("data", result); return map; }
	 * 
	 *//**
		 * 批量保存用户列表
		 * 
		 * @param userDTOList
		 *//*
		 * private batchUsersHandler(List<UserDTO> userDTOList){ StringBuilder
		 * sb = new StringBuilder(3); Iterator<UserDTO> it =
		 * userDTOList.iterator();
		 * 
		 * def result = [:]; while(it.hasNext()) { UserDTO userDTO = it.next();
		 * //这里需要对user属性进行验证 if(StringUtils.isEmpty(userDTO.username)){
		 * continue; } if(StringUtils.isEmpty(userDTO.userCode)){ continue; }
		 * if(StringUtils.isEmpty(userDTO.realName)){ continue; }
		 * if(StringUtils.isEmpty(userDTO.password)){ continue; } //校验所在部门
		 * if(!StringUtils.isNotEmpty(userDTO.organizationId)){ continue; }
		 * //校验职位类别 if(!StringUtils.isNotEmpty(userDTO.jobLevel)){ continue }
		 * //校验办公区 if(!StringUtils.isNotEmpty(userDTO.workPlace)){ continue; }
		 * try { //校验用户编码是否存在 UserDTO userDTO1 =
		 * userAdminFacadeService.getUserByUserCode(userDTO.userCode)
		 * if(userDTO1 != null){ continue; } //校验用户名称是否存在 userDTO1 =
		 * userAdminFacadeService.getUserByUsername(userDTO.username)
		 * if(userDTO1 != null){ continue; }
		 * 
		 * //校验部门机构 def orgs = userDTO.organizationId.split("/")
		 * sb.delete(0,sb.length()); orgs.each { if(StringUtils.isNotEmpty(it))
		 * sb.append("ou=$it,") } sb.append(DEPARTMENT_BASE_DN) Organization
		 * organization = organizationRepoService.getOrganization(sb.toString())
		 * if(organization == null){
		 * 
		 * log.info("=========${userDTO.username}=失败，部门不存在==========")
		 * result."${userDTO.username}" = "失败，部门不存在" continue }
		 * userDTO.setOrganizationId(organization.getId()); //校验直属领导
		 * if(StringUtils.isNotEmpty(userDTO.getDirectLeaderId())){ UserDTO
		 * userDTO2 =
		 * userAdminFacadeService.getUserByUsername(userDTO.getDirectLeaderId())
		 * ; if(userDTO2 == null){
		 * log.info("=========${userDTO.username}=失败，用户已存在==========");
		 * result."${userDTO.username}" = "失败，用户已存在"; continue }
		 * userDTO.directLeaderId = userDTO2.id; } def userId =
		 * userAdminFacadeService.createUser(userDTO); if (userId) {
		 * log.info("=========${userDTO.username}=成功==========");
		 * result."${userDTO.username}" = "成功" } else{
		 * log.info("=========${userDTO.username}=失败==========");
		 * result."${userDTO.username}" = "失败"; }
		 * 
		 * }catch (RuntimeException e){ e.getMessage(); }
		 * 
		 * } result;
		 * 
		 * }
		 */

}
