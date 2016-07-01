package com.shangpin.uaas.configuration;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @description web应用上下文，用于扫描controller(view)层<br/>
 * 这个类可有可无<br/>
 * 注册相关的viewResolver,exceptionResolver,multipartResolver,<br/>
 * il8n,intercepter,messageConverters等等
 * 
 * @author 陈小峰 <br/>
 *         2015年1月30日
 */
@Configuration
@ComponentScan(
		basePackages = "com.shangpin.uaas", 
		includeFilters = {
				@ComponentScan.Filter(
				type=FilterType.ANNOTATION,
				value={Controller.class,RestController.class})
		}
)
public class WebControllerContext extends WebMvcConfigurationSupport {
	static Logger log = LoggerFactory.getLogger(WebControllerContext.class);
	@Autowired
	ResourceInterceptor resourceInterceptor;


	@Override
	@Bean
	public ContentNegotiationManager mvcContentNegotiationManager() {
		log.info("-----------mvcContentNegotiationManager-------");
		ContentNegotiationManager x = super.mvcContentNegotiationManager();
		return x; 
	}
	
	/*@Bean  
	public MessageSource messageSource() {  
	    log.info("-------------MessageResource:MessageSource");  
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();  
	    messageSource.setBasename("messages.messages");  
	    return messageSource;  
	}*/
	/*
	 * 解决json的问题，不同浏览器不支持application/json这种类型，改为text/html方式就ok
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#configureMessageConverters(java.util.List)
	 */
	@Override
	protected void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		MappingJackson2HttpMessageConverter cvt = new MappingJackson2HttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		cvt.setSupportedMediaTypes(supportedMediaTypes );
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.setSerializationInclusion(Include.NON_NULL);
		cvt.setObjectMapper(mapper);
		
		converters.add(cvt);
	}
	@Bean  
    public HandlerAdapter servletHandlerAdapter(){  
        log.info("---------HandlerAdapter");  
        return new SimpleServletHandlerAdapter();  
    }
	
	/*@Bean  
    public LocaleChangeInterceptor localeChangeInterceptor(){  
        log.info("---------LocaleChangeInterceptor");  
        return new LocaleChangeInterceptor();  
    } */
	/*@Bean(name="localeResolver")  
    public CookieLocaleResolver cookieLocaleResolver(){  
        log.info("---------CookieLocaleResolver");  
        return new CookieLocaleResolver();  
    }*/
	
	@Bean  
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {  
        log.info("---------RequestMappingHandlerMapping");  
        return super.requestMappingHandlerMapping();  
    }
	/**
	 * 注册拦截器
	 */
//   protected void addInterceptors(InterceptorRegistry registry) {
//        // TODO Auto-generated method stub
//        log.info("---------addInterceptors start");
//        registry.addInterceptor(resourceInterceptor)
//				.addPathPatterns("/*/**")
//				.excludePathPatterns("*.js")
//				.excludePathPatterns("*.css")
//			   .excludePathPatterns("/userLogin/login")
//			   .excludePathPatterns("/userLogin/logout")
//				.excludePathPatterns("/**/findTopMenusByToken ")
//				.excludePathPatterns("/*facade");
//        log.info("addInterceptors end");
//    }
	@Bean  
    public HandlerMapping resourceHandlerMapping() {  
        log.info("---------HandlerMapping");  
        return super.resourceHandlerMapping();  
    }
	/**
	 * 静态资源处理，可用nginx，apache等处理
	 */
	@Override  
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {  
        log.info("---------addResourceHandlers");  
        registry.addResourceHandler("/*.html","/css/**","/js/**","/images/**","/bower_components/**","/partials/**","/templates/**","/favicon.ico")
        .addResourceLocations("classpath:/static/","classpath:/static/css/","classpath:/static/js/","classpath:/static/images/","classpath:/static/bower_components/","classpath:/static/partials/",
        		"classpath:/static/templates/","classpath:/static/favicon.ico");  
    }
	
	@Bean  
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {  
        log.info("---------RequestMappingHandlerAdapter");  
        return super.requestMappingHandlerAdapter();  
    }
	//编码过滤器
	@Bean
	public FilterRegistrationBean myCharacterEncodingFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 101);
		registration.setFilter(new CharacterEncodingFilter("UTF-8"));
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		//哪些uri需要进行filter处理
		Collection<String> urls = new ArrayList<>();
		urls.add("/*");
		registration.setUrlPatterns(urls);

		return registration;
	}
	//地址过滤器
//	@Bean
//	public FilterRegistrationBean myURLWriterEncodingFilter() {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(new UrlRewriteFilter());
//		//哪些uri需要进行filter处理
//		Collection<String> urls = new ArrayList<>();
//		urls.add("/*");
//		registration.setUrlPatterns(urls);
//		return registration;
//	}



//	//过滤器注册
//	@Bean
//	public FilterRegistrationBean myAccessFilter() {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 102);
//		registration.setFilter(new AccessFilter());
//		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
//		//哪些uri需要进行filter处理
//		Collection<String> urls = new ArrayList<>();
//		urls.add("/*");
//		registration.setUrlPatterns(urls);
//
//		return registration;
//	}


}
