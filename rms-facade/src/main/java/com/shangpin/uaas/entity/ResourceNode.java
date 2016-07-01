package com.shangpin.uaas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.shangpin.uaas.api.admin.resource.ResourceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 资源组
 * @date:     2016年6月10日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceNode implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 2634931950485641592L;
    private String id; // 主键
    private String moduleName; // 模块名称
    private ResourceType type; // 类型
    private String parentResourceId; // 父级模块
    private String description; // 描述
    private String resourceId;

    //联合resource查询的键
    private String enabled;
    private String uri;
}
