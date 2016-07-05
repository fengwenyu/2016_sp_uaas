package com.shangpin.uaas.api.facade.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.shangpin.uaas.api.admin.user.Status;
import com.shangpin.uaas.api.facade.auth.dto.OrganizationDTO;
import com.shangpin.uaas.api.facade.auth.dto.RoleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 系统产生的主键，无业务意义
     * <p/>
     * 创建用户时候，不需要输入
     * <p/>
     * 更新用户时候必须输入
     */
    private String id;

    /**
     * 真实姓名--必须
     */
    private String realName;

    /**
     * 用户名--必须
     */
    private String username;

    /**
     * 性别--枚举--必须
     */
    private String gender;

    /**
     * 用户编号--必须
     */
    private String userCode;

    /**
     * 用户状态--枚举--必须
     */
    private Status status;

    /**
     * 手机号码--建议--登录时候可以使用手机号码登录
     */
    private String mobile;

    /**
     * 座机--可选
     */
    private String telephone;

    /**
     * 邮箱--建议--登录时候可以使用手机号码登录
     */
    private String email;

    /**
     * 直属领导--可选
     */
    private String directLeaderId;

    /**
     * 职位级别：1:;2:;3:;4:;5:;...
     */
    private String jobLevel;

    /**
     * 当前用户可用的角色（状态可用）
     */
    private List<RoleDTO> roles = new ArrayList<RoleDTO>();

    /**
     * 当前用户所在的部门
     */
    private OrganizationDTO organizationDTO;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDirectLeaderId() {
        return directLeaderId;
    }

    public void setDirectLeaderId(String directLeaderId) {
        this.directLeaderId = directLeaderId;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public OrganizationDTO getOrganizationDTO() {
        return organizationDTO;
    }

    public void setOrganizationDTO(OrganizationDTO organizationDTO) {
        this.organizationDTO = organizationDTO;
    }
}
