package com.shangpin.uaas.api.facade.auth;

import java.util.Map;

/**
 * 认证服务
 * <p/>
 * 认证服务提供了登录和注销功能<br />
 * 登录认证服务参考了单点登录SSO和开发协议OAuth，其他系统的登录统一使用该系统进行登录，<br />
 * 在登录成功后，会为该用户发放统一授权的访问令牌（Token），在该令牌（Token）的有效期内可以向系统询问是否有权限访问相关资源。
 */
public interface AuthenticationFacade {

    /**
     * 用户登录
     * <p/>
     * 登录成功后返回系统授权的访问令牌（Token），默认有效期是120分钟
     *
     * @param userLogin 表示用户的身份信息，可以是用户名、手机或者邮箱 -- 必填
     * @param password  密码 -- 必填
     * @return 访问令牌
     */
	public Map<String,String> login(String userLogin, String password);

    /**
     * 延长令牌有效期
     * <p/>
     * <p/>
     * 调用方法后访问令牌（Token）有效期增加20分钟
     *
     * @param token 必填
     */
	public void touch(String token);

    /**
     * 验证Token是否有效
     *
     * @param token 必填
     * @return 是否有效
     */
	public Boolean isValid(String token);

    /**
     * 用户注销
     * <p/>
     * 用户使用访问令牌（Token）请求方法，系统停止该令牌对系统权限或者资源的访问
     *
     * @param token 访问令牌--必填
     */
	public void logout(String token);

}
