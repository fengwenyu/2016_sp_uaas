package com.shangpin.uaas.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import com.shangpin.uaas.entity.e.BooleanStatus;

/**
 * 组织机构
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Organization implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1276664820535521459L;
    private String id;
    private String code;
    private String name;
    private String description;
    private String parentId;
    /**
     * @see BooleanStatus 1可用，0不可用
     */
    private boolean status=true;
    private int sort=0;
    private String leaderIds ;//= new ArrayList<String>();
    
    public List<String> getLeaderIdList(){
    	if(StringUtils.isBlank(leaderIds))
    		return null;
    	return Arrays.asList(leaderIds.split(","));
    }
}
