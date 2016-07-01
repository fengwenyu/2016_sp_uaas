package com.shangpin.uaas;

import com.shangpin.uaas.configuration.ResourceInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.shangpin.uaas.configuration.DataSourceConfigure;
import com.shangpin.uaas.configuration.UserSecurityInterceptor;
@Configuration
@EnableAutoConfiguration(exclude = { HttpMessageConvertersAutoConfiguration.class, JmxAutoConfiguration.class,
		JacksonAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class,
		DataSourceAutoConfiguration.class })
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.shangpin.uaas", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class) })
@Import(value = { DataSourceConfigure.class })
@ImportResource(locations = { "classpath*:/spring/resources.xml", /*"classpath*:/spring/simplesm-context.xml" */})
@EnableCaching
public class UassBootApplication extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(UassBootApplication.class, args);
	}

	/**
	 * 配置拦截器
	 * TODO 放WebControllerContext中
	 * @author lance
	 * @param registry
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserSecurityInterceptor())
				.addPathPatterns("/facade/json/com.shangpin.uaas.api.admin/**");
		/*registry.addInterceptor(new ResourceInterceptor())
				.addPathPatterns("*//*.html");*/
				/*.excludePathPatterns("*.js")
				.excludePathPatterns("*.css")*/
				/*.excludePathPatterns("*.jsp")
				.excludePathPatterns("*.html")*/
				/*.excludePathPatterns("/login.html");*/
	}
}
