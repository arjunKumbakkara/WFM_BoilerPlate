package com.sixdee.wfm;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.sixdee.wfm.controller.DepartmentController;


/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

@SpringBootApplication
@EnableScheduling
@Configuration
// @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableAutoConfiguration
@ComponentScan
@EnableCaching
@EnableJpaAuditing
public class WFMApplication {
	public static Logger logger = LoggerFactory.getLogger(WFMApplication.class);
	public static void main(String[] args) {
		logger.info("|**********************Starting up WORK FORCE MANAGEMENT************************|");
		SpringApplication.run(WFMApplication.class, args);
	}
}
