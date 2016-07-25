package com.shangpin.uaas.convert.api;

import com.shangpin.uaas.api.facade.auth.dto.MenuDTO;
import com.shangpin.uaas.entity.Menu;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class MenuConverter {

	public static MenuDTO convert(Menu menu) {
    	MenuDTO result = new MenuDTO();
        result.setId(menu.getId());
        result.setName(menu.getName());
        result.setUri(menu.getUri());
        result.setParentId(menu.getParentId());
        result.setSort(menu.getSort());
        String appCode = menu.getAppCode();
        String code;
        if(StringUtils.isNotBlank(appCode)){
            code=appCode;
           /* switch (appCode){
                case "PMS":
                    code=APPCode.PMS;
                    break;
                case "CMS":
                        code=APPCode.CMS;
                        break;
                case "FMS":
                        code=APPCode.FMS;
                        break;
                case "UAAS":
                        code=APPCode.UAAS;
                        break;
                case "TMS":
                        code=APPCode.TMS;
                        break;
                case "IOG":
                        code=APPCode.IOG;
                        break;
                case "CCC":
                        code=APPCode.CCC;
                        break;
                case "CSC":
                        code=APPCode.CSC;
                        break;
                case "SCM":
                        code=APPCode.SCM;
                        break;
                default:
                        code=null;
                        break;

                }*/
        }else{
                code=null;
        }
        result.setAppCode(code);
        result.setUrl(menu.getUrl());

        return result;
    }

}
