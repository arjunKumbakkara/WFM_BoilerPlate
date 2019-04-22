package com.sixdee.wfm;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import java.util.Arrays;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {
	@Bean(name = "org.dozer.Mapper")
	public DozerBeanMapper dozerBean() {
		DozerBeanMapper dozerBean = new DozerBeanMapper();
		dozerBean.setMappingFiles(Arrays.asList("dozer-mapping.xml"));
		return dozerBean;
	}
}
