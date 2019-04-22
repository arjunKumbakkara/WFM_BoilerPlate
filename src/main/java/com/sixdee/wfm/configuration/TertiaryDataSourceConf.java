package com.sixdee.wfm.configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
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
  entityManagerFactoryRef = "entityManagerFactoryThree",
  transactionManagerRef = "transactionManagerThree",
  basePackages = { "com.sixdee.wfm.tertiary" }
)
public class TertiaryDataSourceConf {
	@Autowired
	private Environment env;
	public static Logger logger = LogManager.getLogger(TertiaryDataSourceConf.class);
	/*Tertiary  DATASOURCE*/
	@Bean(name = "dataSourceThree")
	@ConfigurationProperties(prefix = "spring.datasource.tertiary")
	public DataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("spring.datasource.tertiary.driver-class-name"));
	    dataSource.setUrl(env.getProperty("spring.datasource.tertiary.url"));
	    dataSource.setUsername(env.getProperty("spring.datasource.tertiary.username"));
	    dataSource.setPassword(env.getProperty("spring.datasource.tertiary.password"));
	    return dataSource;
	    
		//return DataSourceBuilder.create().build();
	}
	@Bean(name = "entityManagerFactoryThree")
	public LocalContainerEntityManagerFactoryBean 
	entityManagerFactoryThree(EntityManagerFactoryBuilder builder,@Qualifier("dataSourceThree") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.sixdee.wfm").persistenceUnit("thirdPersistenceUnit").build();
	}
	@Bean(name = "transactionManagerThree")
	public PlatformTransactionManager transactionManagerThree(@Qualifier("entityManagerFactoryThree") EntityManagerFactory entityManagerFactory){
		return new JpaTransactionManager(entityManagerFactory);
	}
	/*---------------------------------------------------------------------------------------------------------------------------------------*/
	@Bean(name = "jtemplate_dataSourceThree")
    @Autowired
    public JdbcTemplate thirdJdbcTemplate(@Qualifier("dataSourceThree") DataSource dataSourceThree) {
        return new JdbcTemplate(dataSourceThree);
    }
}
