package com.coffeeinfinitive.config;

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
@ComponentScan({ "com.coffeeinfinitive.dao" })
@EnableTransactionManagement
@PropertySource(value = {"classpath:properties/database.properties"})
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(env.getProperty("hikari.dataSourceClassName"));
        config.setJdbcUrl(env.getProperty("hikari.dataSource.url"));
        config.setUsername(env.getProperty("hikari.dataSource.username"));
        config.setPassword(env.getProperty("hikari.dataSource.password"));
        config.setMaximumPoolSize(Integer.parseInt(env.getProperty("hikari.maximumPoolSize")));
        config.setIdleTimeout(Long.parseLong(env.getProperty("hikari.idleTimeout")));
        config.setMinimumIdle(Integer.parseInt(env.getProperty("hikari.minimumIdle")));
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.coffeeinfinitive.dao.entity" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    private Properties hibernateProperties() {
        Properties properties = new Properties();
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
