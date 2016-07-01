package com.shangpin.uaas.convert;

import com.shangpin.common.utils.DateUtil;
import com.shangpin.uaas.api.admin.user.Status;
import com.shangpin.uaas.api.admin.user.UserDTO;
import com.shangpin.uaas.api.admin.user.UserWithGroupDTO;
import com.shangpin.uaas.entity.User;

/**
 * 用户转换器
 */
public class UserConverter {

   public static UserDTO toUserDTO(User user) {
    	UserDTO result = new UserDTO();
        result.setId(user.getId());
        result.setDirectLeaderId(user.getDirectLeaderId());
        result.setEmail(user.getEmail());
        result.setJobLevel(user.getJobLevel());
        result.setMobile(user.getMobile());

        result.setGender(user.getGender());
        result.setOrganizationId(user.getOrganizationId());
        result.setOrganizationName(user.getOrganizationName());
        result.setRealName(user.getRealName());
        result.setStatus(user.isStatus()?Status.ENABLED:Status.DISABLED);
        result.setPassword(user.getPassword());
        result.setTelephone(user.getTelephone());
        result.setUserCode(user.getUserCode());
        result.setDirectLeaderId(user.getDirectLeaderId());
        result.setUsername(user.getUsername());
        result.setWorkPlace(user.getWorkplace());
        result.setCreatedTime(DateUtil.datetime19(user.getCreatedTime()));
        result.setBirth(user.getBirth()!=null?DateUtil.date10(user.getBirth()):"");
        result.setJobTitle(user.getJobTitle());
        result.setCompany(user.getCompany());
        result.setEntryDate(user.getEntryDate());
        result.setJobTitleDate(user.getJobTitleDate());
        result.setContractStartDate(user.getContractStartDate()!=null?DateUtil.date10(user.getContractStartDate()):"");
        result.setProbationEndDate(user.getProbationEndDate()!=null?DateUtil.date10(user.getProbationEndDate()):"");
        result.setPositiveDate(user.getPositiveDate()!=null?DateUtil.date10(user.getPositiveDate()):"");
        result.setFirstContractEndDate(user.getFirstContractEndDate()!=null?DateUtil.date10(user.getFirstContractEndDate()):"");
        result.setSecondContractEndDate(user.getSecondContractEndDate()!=null?DateUtil.date10(user.getSecondContractEndDate()):"");
        result.setTurnoverDate(user.getTurnoverDate()!=null?DateUtil.date10(user.getTurnoverDate()):"");
        result.setTurnoverDate(user.getTurnoverDate()!=null?DateUtil.date10(user.getTurnoverDate()):"");
        result.setEmployeeStatus(user.getEmployeeStatus());
        result.setIdentity(user.getIdentity());
        result.setIdentityNumber(user.getIdentityNumber());
        result.setAge(user.getAge());
        result.setNation(user.getNation());
        result.setNationality(user.getNationality());
        result.setEducation(user.getEducation());
        result.setDegree(user.getDegree());
        result.setLearningType(user.getLearningType());
        result.setPoliticalAffiliation(user.getPoliticalAffiliation());
        result.setWorkStartDate(user.getWorkStartDate()!=null?DateUtil.date10(user.getWorkStartDate()):"");
        result.setGraduationTime(user.getGraduationTime());
        result.setFamilyRegister(user.getFamilyRegister());
        result.setForeignEnglish(user.getForeignEnglish());
        result.setForeignRussian(user.getForeignRussian());
        result.setForeignFrench(user.getForeignFrench());
        result.setForeignKorean(user.getForeignKorean());
        result.setForeignJapanese(user.getForeignJapanese());
               
        result.setMaritalStatus(user.getMaritalStatus());
        result.setBirthPlace(user.getBirthPlace());
        result.setSalaryBank(user.getSalaryBank());
        result.setSalaryBankNumber(user.getSalaryBankNumber());
          result.setRoleId(user.getRoleId());
        return result;
    }

   public static UserWithGroupDTO toUserWithGroupDTO (User user) {
    	UserWithGroupDTO result = new UserWithGroupDTO();
        result.setId(user.getId());
        result.setDirectLeaderId(user.getDirectLeaderId());
        result.setEmail(user.getEmail());
        result.setJobLevel(user.getJobLevel());
        result.setMobile(user.getMobile());
        
        result.setGender(user.getGender());
        result.setOrganizationId(user.getOrganizationId());
        result.setRealName(user.getRealName());
        result.setStatus(user.isStatus()?Status.ENABLED:Status.DISABLED);
        result.setPassword(user.getPassword());
        result.setTelephone(user.getTelephone());
        result.setUserCode(user.getUserCode());
        result.setDirectLeaderId(user.getDirectLeaderId());
        result.setUsername(user.getUsername());
        result.setWorkPlace(user.getWorkplace());
        result.setCreatedTime(DateUtil.datetime19(user.getCreatedTime()));
        result.setBirth(user.getBirth()!=null?DateUtil.date10(user.getBirth()):"");
        result.setHasThisGroup(false);



        return result;
    }

}
