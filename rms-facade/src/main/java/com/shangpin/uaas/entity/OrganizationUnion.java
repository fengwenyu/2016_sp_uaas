package com.shangpin.uaas.entity;

import com.shangpin.uaas.entity.e.BooleanStatus;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 组织机构
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationUnion implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1276664820535521459L;
    private String id;
    private String name1;
    private String name2;
    private String name3;
    private String name4;
}
