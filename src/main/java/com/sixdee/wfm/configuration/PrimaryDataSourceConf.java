package com.sixdee.wfm.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

import com.sixdee.wfm.base.GlobalRepositoryImpl;
/**
 * @author ArjunKumbakkara
 * @version 1.0.0
 * @sixdee
 * Configures the data sources we will be working with. The actual configuration parameters are
 * delegated to the environment (e.g. resources/application.properties).
 */

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(
  entityManagerFactoryRef = "entityManagerFactoryOne",
  transactionManagerRef = "transactionManagerOne",
  basePackages = { "com.sixdee.wfm" },repositoryBaseClass = GlobalRepositoryImpl.class
)
/*repositoryBaseClass = ExtendedRepositoryImpl.class : tells spring to use our custom implementation for building repository implementations*/
public class PrimaryDataSourceConf {

	@Autowired
	private Environment env;
	
	public static Logger logger = LogManager.getLogger(PrimaryDataSourceConf.class);

	/*##################################################################################################################
    TYPE-1:	                                                         JNDI
	##################################################################################################################*/
	/*UNCOMMENT FOR JNDI------------------------------------------------------------------------------------(1)*/
	/*@Bean(name = "first")
    @Primary
    public DataSource dataSourceOne() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource("java:jboss/datasources/MySqlDS");
        return dataSource;
    }*/
	//-------------------------------------------------------------------------------------------------------

	/*UNCOMMENT FOR JNDI------------------------------------------------------------------------------------(1)*/
	/*@Bean(name = "second")
    public DataSource dataSourceTwo() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource("java:jboss/datasources/MySqlDS1");
        return dataSource;
    }*/
	//-------------------------------------------------------------------------------------------------------


	/*UNCOMMENT FOR JNDI------------------------------------------------------------------------------------(1)*/
	/*	@Bean(name = "third")
    public DataSource dataSourceThree() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource("java:jboss/datasources/MySqlDS1");
        return dataSource;
    }*/
	//-------------------------------------------------------------------------------------------------------
	/*##################################################################################################################
	TYPE-2:                                                   IN SYSTEM (Application.properties, MYSQL , JDBC)
	##################################################################################################################*/
	/*		UNCOMMENTED FOR IN SYSTEM DATABASE
			@Bean(name = "first")
		    @Primary
		    @ConfigurationProperties(prefix = "datasource.ds1")
	 *//**
	 * Data source one (also the primary/default). By default, the name of the method ("dataSourceOne") will be used for
	 * the datasource name. Or we can use @Bean(name="...").
	 *//*
		    public DataSource dataSourceOne() {
		    	logger.info("...............DATASOURCE INTIALIZED FOR THE ENGINE.............");
		    	org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		    	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		    	return dataSource;
			}
			UNCOMMENTED FOR IN SYSTEM DATABASE
			@Bean(name = "second")
		    @ConfigurationProperties(prefix = "datasource.ds2")
	  *//**
	  * Data source two. By default, the name of the method ("dataSourceTwo") will be used for
	  * the datasource name. Or we can use @Bean(name="...").
	  *//*
		    public DataSource dataSourceTwo() {
		    	org.apache.tomcat.jdbc.pool.DataSource dataSource 
		        = new org.apache.tomcat.jdbc.pool.DataSource();

		    	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		    	return dataSource;
		    }
			UNCOMMENTED FOR IN SYSTEM DATABASE
			@Bean(name = "second")
		    @ConfigurationProperties(prefix = "datasource.ds3")
	   *//**
	   * Data source one (also the primary/default). By default, the name of the method ("dataSourceOne") will be used for
	   * the datasource name. Or we can use @Bean(name="...").
	   *//*
		    public DataSource dataSourceThree() {
		    	logger.info("...............DATASOURCE INTIALIZED FOR THE ENGINE.............");
		    	org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		    	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		    	return dataSource;
		    }*/
	/*----------------------------------------------------------------JDBC TEMPLATE WIRING(NOT REQUIRED HERE)-------------------------------------------------------------------------------------------*/
	/*@Bean(name = "templateFromDS1")
    @Autowired
    public JdbcTemplate firstJdbcTemplate(@Qualifier("first") DataSource first) {
        return new JdbcTemplate(first);
    }
    @Bean(name = "templateFromDS2")
    @Autowired
    public JdbcTemplate secondJdbcTemplate(@Qualifier("second") DataSource second) {
        return new JdbcTemplate(second);
    }
    @Bean(name = "templateFromDS3")
    @Autowired
    public JdbcTemplate thirdJdbcTemplate(@Qualifier("third") DataSource third) {
        return new JdbcTemplate(third);
    }*/
	
	
	
	/*##################################################################JPA###################################################################*/
	//TYPE-3:
	/*#######################################################################################################################################*/
	/*Primary  DATASOURCE*/
	@Primary
	@Bean(name = "dataSourceOne")
	@ConfigurationProperties(prefix = "spring.datasource.primary")
	public DataSource dataSourceOne() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("spring.datasource.primary.driver-class-name"));
	    dataSource.setUrl(env.getProperty("spring.datasource.primary.url"));
	    dataSource.setUsername(env.getProperty("spring.datasource.primary.username"));
	    dataSource.setPassword(env.getProperty("spring.datasource.primary.password"));
		//return DataSourceBuilder.create().build();
	    return dataSource;
	}
	@Primary
	@Bean(name = "entityManagerFactoryOne")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryOne(EntityManagerFactoryBuilder builder,@Qualifier("dataSourceOne") DataSource dataSource){
		return builder.dataSource(dataSource).packages("com.sixdee.wfm").persistenceUnit("firstPersistenceUnit").build();
	}

	@Primary
	@Bean(name = "transactionManagerOne")
	public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactoryOne") EntityManagerFactory  entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	//-----------------------------------------------------------Autowiring a JDBC Template for custom Queries---------------------------------------------\
	
	@Bean(name = "jtemplate_dataSourceOne")
    @Autowired
    public JdbcTemplate firstJdbcTemplate(@Qualifier("dataSourceOne") DataSource dataSourceOne) {
        return new JdbcTemplate(dataSourceOne);
    }
	
}
