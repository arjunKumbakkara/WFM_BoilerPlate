package com.sixdee.wfm.configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @author ArjunKumbakkara
 * @version 1.0.0
 * @sixdee
 * Configures the data sources we will be working with. The actual configuration parameters are
 * delegated to the environment (e.g. resources/application.properties).
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  entityManagerFactoryRef = "entityManagerFactoryTwo",
  transactionManagerRef = "transactionManagerTwo",
  basePackages = { "com.sixdee.wfm.secondary" }
)
public class SecondaryDataSourceConf {
	@Autowired
	private Environment env;
	public static Logger logger = LogManager.getLogger(SecondaryDataSourceConf.class);

	/*Secondary  DATASOURCE*/
	@Bean(name = "dataSourceTwo")
	@ConfigurationProperties(prefix = "spring.datasource.secondary")
	public DataSource dataSourceTwo() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("spring.datasource.secondary.driver-class-name"));
	    dataSource.setUrl(env.getProperty("spring.datasource.secondary.url"));
	    dataSource.setUsername(env.getProperty("spring.datasource.secondary.username"));
	    dataSource.setPassword(env.getProperty("spring.datasource.secondary.password"));
	    return dataSource;
		//return DataSourceBuilder.create().build();
	}
	@Bean(name = "entityManagerFactoryTwo")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryTwo(EntityManagerFactoryBuilder builder,@Qualifier("dataSourceTwo") DataSource dataSourceTwo) {
		return builder.dataSource(dataSourceTwo).packages("com.sixdee.wfm").persistenceUnit("secondPersistenceUnit").build();
	}
	@Bean(name = "transactionManagerTwo")
	public PlatformTransactionManager transactionManagerTwo(@Qualifier("entityManagerFactoryTwo") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}


	/*---------------------------------------------------------------------------------------------------------------------------------------*/
	@Bean(name = "jtemplate_dataSourceTwo")
    @Autowired
    public JdbcTemplate secondJdbcTemplate(@Qualifier("dataSourceTwo") DataSource dataSourceTwo) {
        return new JdbcTemplate(dataSourceTwo);
    }
}
