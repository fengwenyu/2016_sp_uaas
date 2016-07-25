package com.shangpin.uaas.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 
 */
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Menu implements Comparable<Menu>,Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3372447398618016603L;
    private String id;
    private String uri;
    private String parentId;
    private String name;
    private int sort=0;
    private String appCode;//1.PMS,2.CMS,3.FMS,4UAAS,5TMS,6IOG,7CCC,8CSC,9SCM,
    private String url;


    public int compareTo(Menu o) {
        if(this.sort>o.sort){
        	return 1;
        }else{
        	return -1;
        }
        /*this.sort = this.sort == null ? "0" : this.sort;
        o.setSort(o.getSort() == null ? "0" : o.getSort());
        if(this.sort.hashCode() >= o.getSort().hashCode()){
            return 1;
        }else{
            return -1;
        }*/
    }

}
