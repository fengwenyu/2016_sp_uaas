package com.shangpin.uaas.api.facade.user;
 

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @date:     2016年6月11日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
@Setter
@Getter
public class Subject implements Serializable{

    private static final long serialVersionUID = 1L;

    private String token;
    private String username;
    private String userCode;
    //private String dn;
    private String userId;


}
