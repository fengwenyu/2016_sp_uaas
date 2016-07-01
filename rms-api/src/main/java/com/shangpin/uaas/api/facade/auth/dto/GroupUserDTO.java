package com.shangpin.uaas.api.facade.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * date :  2014-8-19.
 */
@Setter
@Getter
public class GroupUserDTO {
    private String groupId;
    private String userId;
    private String groupName;
    private String userName;
    private String groupDes;
    private String isEnabled;

}
