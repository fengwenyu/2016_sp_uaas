package com.shangpin.uaas.controller.admin;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2014/8/5.
 */
@Setter
@Getter
public class UserRequest {
   private String username;
   private String password;
   private String verifyCode;
}
