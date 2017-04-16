package com.mservice.sdk.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by jinz on 4/16/17.
 */

@Configuration
@ComponentScan({ "com.mservice.sdk.config" })
@EnableTransactionManagement
@PropertySource(value = {"classpath:abc.properties"})
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        //config.setDriverClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/springmvc");
        config.setUsername(env.getProperty("hibernate.hikari.dataSource.username"));
        config.setPassword(env.getProperty("hibernate.hikari.dataSource.password"));
        HikariDataSource dataSource = new HikariDataSource(config);


//        dataSource.setMaximumPoolSize(Integer.parseInt(env.getProperty("hibernate.hikari.maximumPoolSize")));
//        dataSource.setDataSourceClassName(env.getProperty("hibernate.hikari.dataSourceClassName"));
       //dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/springmvc");
//        dataSource.setUsername(env.getProperty("hibernate.hikari.dataSource.username"));
//        dataSource.setPassword(env.getProperty("hibernate.hikari.dataSource.password"));
        dataSource.addDataSourceProperty("dataSource","");
        dataSource.addDataSourceProperty("jdbcUrl", env.getProperty("hibernate.hikari.dataSource.username"));
        dataSource.addDataSourceProperty("dataSourceClassName", env.getProperty("hibernate.hikari.dataSource.password"));
         return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.mservice.sdk.dao.entity" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        //properties.put("connection.provider_class",env.getRequiredProperty("hibernate.connection.provider_class"));
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.connection.CharSet", env.getRequiredProperty("hibernate.connection.CharSet"));
        properties.put("hibernate.connection.characterEncoding", env.getRequiredProperty("hibernate.connection.characterEncoding"));
        properties.put("hibernate.connection.useUnicode", env.getRequiredProperty("hibernate.connection.useUnicode"));
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

}
