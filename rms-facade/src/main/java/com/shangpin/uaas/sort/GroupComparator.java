package com.shangpin.uaas.sort;

import java.util.Comparator;

import com.shangpin.uaas.entity.Group;

/**
 * 
 * @date:     2016年6月10日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
public class GroupComparator implements Comparator<Group> {
	@Override
	public int compare(Group o1, Group o2) {
		if (o1.getCreatedTime().before(o2.getCreatedTime())) {
			return 1;
		} else {
			return -1;
		}
		
	}
}
