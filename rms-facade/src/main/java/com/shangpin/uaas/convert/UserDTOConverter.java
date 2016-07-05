package com.shangpin.uaas.convert;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import com.shangpin.common.utils.DateUtil;
import com.shangpin.uaas.api.admin.user.Status;
import com.shangpin.uaas.api.admin.user.UserDTO;
import com.shangpin.uaas.entity.User;

/**
 * 用户转换器
 *
 */
//@Category(UserDTO)
public class UserDTOConverter {

   public static User toUserEntity(UserDTO userDTO) {
    	User result = new User();

        result.setId(UUID.randomUUID().toString());

        if (StringUtils.isNotEmpty(userDTO.getUserCode())) {
            result.setUserCode(userDTO.getUserCode());
        }
        if (StringUtils.isNotEmpty(userDTO.getRealName())) {
            result.setRealName(userDTO.getRealName());
        }
        if (StringUtils.isNotEmpty(userDTO.getOrganizationId())) {
            result.setOrganizationId(userDTO.getOrganizationId());
        }
        if (StringUtils.isNotEmpty(userDTO.getMobile())) {
            result.setMobile(userDTO.getMobile());
        }
        if (StringUtils.isNotEmpty(userDTO.getTelephone())) {
            result.setTelephone(userDTO.getTelephone());
        }
        result.setCreatedTime(new Date());
        if (StringUtils.isNotEmpty(userDTO.getDirectLeaderId())) {
            result.setDirectLeaderId(userDTO.getDirectLeaderId());
        }
        if (StringUtils.isNotEmpty(userDTO.getEmail())) {
            result.setEmail(userDTO.getEmail());
        }
        result.setGender(userDTO.getGender());

        if (StringUtils.isNotEmpty(userDTO.getJobLevel())) {
            result.setJobLevel(userDTO.getJobLevel());
        }
        result.setStatus("ENABLED".equals(userDTO.getStatus().name())?true:false);
        if (StringUtils.isNotEmpty(userDTO.getUsername())) {
            result.setUsername(userDTO.getUsername());
        }
        if (StringUtils.isNotEmpty(userDTO.getPassword())) {
            result.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
        }
        if (StringUtils.isNotEmpty(userDTO.getBirth())) {
            result.setBirth(DateUtil.parseDate10(userDTO.getBirth()));
        }
        if (StringUtils.isNotEmpty(userDTO.getWorkPlace())) {
            result.setWorkplace(userDTO.getWorkPlace());
        }

        if (StringUtils.isNotEmpty(userDTO.getJobTitle())) {
            result.setJobTitle(userDTO.getJobTitle());
        }
        if (StringUtils.isNotEmpty(userDTO.getCompany())) {
            result.setCompany(userDTO.getCompany());
        }
        if (StringUtils.isNotEmpty(userDTO.getEntryDate())) {
            result.setEntryDate(userDTO.getEntryDate());
        }
        if (StringUtils.isNotEmpty(userDTO.getJobTitleDate())) {
            result.setJobTitleDate(userDTO.getJobTitleDate());
        }
        if (StringUtils.isNotEmpty(userDTO.getContractStartDate())) {
            result.setContractStartDate(DateUtil.parseDate10(userDTO.getContractStartDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getProbationEndDate())) {
            result.setProbationEndDate(DateUtil.parseDate10(userDTO.getProbationEndDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getPositiveDate())) {
            result.setPositiveDate(DateUtil.parseDate10(userDTO.getPositiveDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getFirstContractEndDate())) {
            result.setFirstContractEndDate(DateUtil.parseDate10(userDTO.getFirstContractEndDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getSecondContractEndDate())) {
            result.setSecondContractEndDate(DateUtil.parseDate10(userDTO.getSecondContractEndDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getTurnoverDate())) {
            result.setTurnoverDate(DateUtil.parseDate10(userDTO.getTurnoverDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getTurnoverDate())) {
            result.setTurnoverDate(DateUtil.parseDate10(userDTO.getTurnoverDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getEmployeeStatus())) {
            result.setEmployeeStatus(userDTO.getEmployeeStatus());
        }


        if (StringUtils.isNotEmpty(userDTO.getIdentity())) {
            result.setIdentity(userDTO.getIdentity());
        }
        if (StringUtils.isNotEmpty(userDTO.getIdentityNumber())) {
            result.setIdentityNumber(userDTO.getIdentityNumber());
        }
        if (StringUtils.isNotEmpty(userDTO.getAge())) {
            result.setAge(userDTO.getAge());
        }
        if (StringUtils.isNotEmpty(userDTO.getNation())) {
            result.setNation(userDTO.getNation());
        }
        if (StringUtils.isNotEmpty(userDTO.getNationality())) {
            result.setNationality(userDTO.getNationality());
        }
        if (StringUtils.isNotEmpty(userDTO.getEducation())) {
            result.setEducation(userDTO.getEducation());
        }
        if (StringUtils.isNotEmpty(userDTO.getDegree())) {
            result.setDegree(userDTO.getDegree());
        }
        if (StringUtils.isNotEmpty(userDTO.getLearningType())) {
            result.setLearningType(userDTO.getLearningType());
        }
        if (StringUtils.isNotEmpty(userDTO.getPoliticalAffiliation())) {
            result.setPoliticalAffiliation(userDTO.getPoliticalAffiliation());
        }
        if (StringUtils.isNotEmpty(userDTO.getWorkStartDate())) {
            result.setWorkStartDate(DateUtil.parseDate10(userDTO.getWorkStartDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getGraduationTime())) {
            result.setGraduationTime(userDTO.getGraduationTime());
        }
        if (StringUtils.isNotEmpty(userDTO.getFamilyRegister())) {
            result.setFamilyRegister(userDTO.getFamilyRegister());
        }

        if (userDTO.getForeignEnglish()!=null) {
            result.setForeignEnglish(userDTO.getForeignEnglish()+"");
        }
        if (userDTO.getForeignRussian()!=null) {
            result.setForeignRussian(userDTO.getForeignRussian()+"");
        }
        if (userDTO.getForeignFrench()!=null) {
            result.setForeignFrench(userDTO.getForeignFrench()+"");
        }
        if (userDTO.getForeignKorean()!=null) {
            result.setForeignKorean(userDTO.getForeignKorean()+"");
        }
        if (userDTO.getForeignJapanese()!=null) {
            result.setForeignJapanese(userDTO.getForeignJapanese()+"");
        }

        if (StringUtils.isNotEmpty(userDTO.getMaritalStatus())) {
            result.setMaritalStatus(userDTO.getMaritalStatus());
        }
        if (StringUtils.isNotEmpty(userDTO.getBirthPlace())) {
            result.setBirthPlace(userDTO.getBirthPlace());
        }
        if (StringUtils.isNotEmpty(userDTO.getSalaryBank())) {
            result.setSalaryBank(userDTO.getSalaryBank());
        }
        if (StringUtils.isNotEmpty(userDTO.getSalaryBankNumber())) {
            result.setSalaryBankNumber(userDTO.getSalaryBankNumber());
        }
        return result;
    }

    /**
     * @param user
     * @param userDTO
     * @return
     */
    public static User toUpdateEntity(User user,UserDTO userDTO) {
        if (StringUtils.isNotEmpty(userDTO.getUserCode())) {
            user.setUserCode(userDTO.getUserCode());
        }
        if (StringUtils.isNotEmpty(userDTO.getRealName())) {
            user.setRealName(userDTO.getRealName());
        }
        if (StringUtils.isNotEmpty(userDTO.getMobile())) {
            user.setMobile(userDTO.getMobile());
        } else {
            user.setMobile(null);
        }
        if (StringUtils.isNotEmpty(userDTO.getTelephone())) {
            user.setTelephone(userDTO.getTelephone());
        } else {
            user.setTelephone(null);
        }
        user.setUpdatedTime(new Date());
        if (StringUtils.isNotEmpty(userDTO.getDirectLeaderId())) {
            user.setDirectLeaderId(userDTO.getDirectLeaderId());
        } else {
            user.setDirectLeaderId(null);
        }
        if (StringUtils.isNotEmpty(userDTO.getEmail())) {
            user.setEmail(userDTO.getEmail());
        } else {
            user.setEmail(null);
        }
        user.setGender(userDTO.getGender());

        if (StringUtils.isNotEmpty(userDTO.getJobLevel())) {
            user.setJobLevel(userDTO.getJobLevel());
        }
        if (StringUtils.isNotEmpty(userDTO.getUsername())) {
            user.setUsername(userDTO.getUsername());
        }
        if (StringUtils.isNotEmpty(userDTO.getBirth())) {
            user.setBirth(DateUtil.parseDate10(userDTO.getBirth()));
        } else {
            user.setBirth(null);
        }
        if (StringUtils.isNotEmpty(userDTO.getWorkPlace())) {
            user.setWorkplace(userDTO.getWorkPlace());
        } else {
            user.setWorkplace(null);
        }
        if(StringUtils.isNotEmpty(userDTO.getPassword())){
            user.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
        }else{
            user.setPassword(null);
        }


        if (StringUtils.isNotEmpty(userDTO.getJobTitle())) {
            user.setJobTitle(userDTO.getJobTitle());
        }
        if (StringUtils.isNotEmpty(userDTO.getCompany())) {
            user.setCompany(userDTO.getCompany());
        }
        if (StringUtils.isNotEmpty(userDTO.getEntryDate())) {
            user.setEntryDate(userDTO.getEntryDate());
        }
        if (StringUtils.isNotEmpty(userDTO.getJobTitleDate())) {
            user.setJobTitleDate(userDTO.getJobTitleDate());
        }
        if (StringUtils.isNotEmpty(userDTO.getContractStartDate())) {
            user.setContractStartDate(DateUtil.parseDate10(userDTO.getContractStartDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getProbationEndDate())) {
            user.setProbationEndDate(DateUtil.parseDate10(userDTO.getProbationEndDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getPositiveDate())) {
            user.setPositiveDate(DateUtil.parseDate10(userDTO.getPositiveDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getFirstContractEndDate())) {
            user.setFirstContractEndDate(DateUtil.parseDate10(userDTO.getFirstContractEndDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getSecondContractEndDate())) {
            user.setSecondContractEndDate(DateUtil.parseDate10(userDTO.getSecondContractEndDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getTurnoverDate())) {
            user.setTurnoverDate(DateUtil.parseDate10(userDTO.getTurnoverDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getEmployeeStatus())) {
            user.setEmployeeStatus(userDTO.getEmployeeStatus());
        }


        if (StringUtils.isNotEmpty(userDTO.getIdentity())) {
            user.setIdentity(userDTO.getIdentity());
        }
        if (StringUtils.isNotEmpty(userDTO.getIdentityNumber())) {
            user.setIdentityNumber(userDTO.getIdentityNumber());
        }
        if (StringUtils.isNotEmpty(userDTO.getAge())) {
            user.setAge(userDTO.getAge());
        }
        if (StringUtils.isNotEmpty(userDTO.getNation())) {
            user.setNation(userDTO.getNation());
        }
        if (StringUtils.isNotEmpty(userDTO.getNationality())) {
            user.setNationality( userDTO.getNationality());
        }
        if (StringUtils.isNotEmpty(userDTO.getEducation())) {
            user.setEducation(userDTO.getEducation());
        }
        if (StringUtils.isNotEmpty(userDTO.getDegree())) {
            user.setDegree(userDTO.getDegree());
        }
        if (StringUtils.isNotEmpty(userDTO.getLearningType())) {
            user.setLearningType(userDTO.getLearningType());
        }
        if (StringUtils.isNotEmpty(userDTO.getPoliticalAffiliation())) {
            user.setPoliticalAffiliation(userDTO.getPoliticalAffiliation());
        }
        if (StringUtils.isNotEmpty(userDTO.getWorkStartDate())) {
            user.setWorkStartDate(DateUtil.parseDate10(userDTO.getWorkStartDate()));
        }
        if (StringUtils.isNotEmpty(userDTO.getGraduationTime())) {
            user.setGraduationTime(userDTO.getGraduationTime());
        }
        if (StringUtils.isNotEmpty(userDTO.getFamilyRegister())) {
            user.setFamilyRegister(userDTO.getFamilyRegister());
        }
        if (userDTO.getForeignEnglish()!=null) {
            user.setForeignEnglish(userDTO.getForeignEnglish()+"");
        }
        if (userDTO.getForeignRussian()!=null) {
            user.setForeignRussian( userDTO.getForeignRussian()+"");
        }
        if (userDTO.getForeignFrench()!=null) {
            user.setForeignFrench(userDTO.getForeignFrench()+"");
        }
        if (userDTO.getForeignKorean()!=null) {
            user.setForeignKorean(userDTO.getForeignKorean()+"");
        }
        if (userDTO.getForeignJapanese()!=null) {
            user.setForeignJapanese(userDTO.getForeignJapanese()+"");
        }
        if (StringUtils.isNotEmpty(userDTO.getMaritalStatus())) {
            user.setMaritalStatus(userDTO.getMaritalStatus());
        }
        if (StringUtils.isNotEmpty(userDTO.getBirthPlace())) {
            user.setBirthPlace( userDTO.getBirthPlace());
        }
        if (StringUtils.isNotEmpty(userDTO.getSalaryBank())) {
            user.setSalaryBank(userDTO.getSalaryBank());
        }
        if (StringUtils.isNotEmpty(userDTO.getSalaryBankNumber())) {
            user.setSalaryBankNumber(userDTO.getSalaryBankNumber());
        }

        return user;
    }

}
