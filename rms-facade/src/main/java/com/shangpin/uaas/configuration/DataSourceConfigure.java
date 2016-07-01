package com.shangpin.uaas.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * 数据库配置
 * @description 
 * @author 陈小峰
 * <br/>2015年8月8日
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfigure {
	
	@Bean(destroyMethod="close")
	@ConfigurationProperties(prefix="shangpin.uaas.dataSource.hikari")
	public DataSource dataSource() {
		HikariDataSource ds = new HikariDataSource();
		return ds;
	}
}
