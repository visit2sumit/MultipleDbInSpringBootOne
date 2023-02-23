package com.giit.db.postgres.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondEntityManagerFactoryBean",
        basePackages = {"com.giit.db.postgres.repo"},
        transactionManagerRef = "secondTransactionManager"
)
public class PostgresDbConfig {

    @Autowired
    private Environment env;
    //datasource

    @Bean(name = "secondDataSource")
    @Primary
    public DataSource dataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("second.datasource.url"));
        dataSource.setDriverClassName(env.getProperty("second.datasource.driver-class-name"));
        dataSource.setUsername(env.getProperty("second.datasource.username"));
        dataSource.setPassword(env.getProperty("second.datasource.password"));
        return dataSource;
    }
    //entityManagerFactory
    @Primary
    @Bean(name = "secondEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){

        LocalContainerEntityManagerFactoryBean bean=new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        JpaVendorAdapter adapter=new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        Map<String,String> property=new HashMap<>();
        property.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
        property.put("hibernate.show_sql","true");
        property.put("hibernate.hbm2ddl.auto","update");
        bean.setJpaPropertyMap(property);
        bean.setPackagesToScan("com.giit.db.postgres.entities");
        return  bean;
    }
    //platformTransactionManager
    @Primary
    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager manager=new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return manager;
    }
}
