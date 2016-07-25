package com.shangpin.uaas.api.facade.auth.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 菜单
 */
@Setter
@Getter
@EqualsAndHashCode(exclude={"sort","uri","parentId","appCode","url"})
public class MenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键唯一标识
     */
    private String id;
    /**
     * 显示名称
     */
    private String name;
    /**
     * uri
     */
    private String uri;
    /**
     * 父级标识
     */
    private String parentId;

    /**
     * 客户端可以根据该字段排序后来显示顺序
     */
    private int sort;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 菜单要跳转的目标地址
     */
    private String url;
}
