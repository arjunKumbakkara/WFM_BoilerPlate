package com.sixdee.wfm;
/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.sixdee.wfm")).paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndPointsInfo())
				.securitySchemes(Lists.newArrayList(apiKey()));
	}

	private ApiKey apiKey() {
		return new ApiKey("X-Authorization", "Authorization", "header");
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("WFM Stateless REST API").description("Work Force Management").contact(new Contact("6dTechnologies", "http://6dtech.co.in/", "info@6dtech.co.in"))
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0.0").build();
	}

	@SuppressWarnings("deprecation")
	@Bean
	public SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, null, "Bearer ", ApiKeyVehicle.HEADER, "X-Authorization", "");
	}

}
