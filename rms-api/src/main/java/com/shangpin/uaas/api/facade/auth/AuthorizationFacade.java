package com.shangpin.uaas.api.facade.auth;

import java.util.List;

/**
 * 权限服务<br />
 * 系统登录（参考AuthenticationFacade接口）成功后，系统为该用户分配一个Token，<br />
 * 使用该Token可以对资源询问接口是否准许访问
 */
public interface AuthorizationFacade {

    /**
     * 是否允许访问
     * <p/>
     * <p/>
     * 用户输入一个URI，系统检查该访问令牌（Token）是否可以访问这个URI
     *
     * @param token       访问令牌 必填
     * @param resourceUri 资源（权限）必填
     * @return 是否可访问
     */
    boolean isPermitted(String token, String resourceUri);

    /**
     * 是否允许访问集合
     *
     * @param token        必须
     * @param resourceUris 资源列表（权限） 1..n
     * @return 返回结果集顺序与resourceUris顺序一致
     */
    List<Boolean> isPermitted(String token, List<String> resourceUris);


}
