package com.example.library.db;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.Database;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.example.library.repositories")
@EnableTransactionManagement
@ComponentScan(basePackages = "com.example.library.db")
public class PersistenceJPAConfig{

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean em 
        = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource());
      em.setPackagesToScan(new String[] { "com.example.library.entities" });
      
      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

      em.setJpaVendorAdapter(vendorAdapter);
      
      Properties jpaProperties = new Properties();


      jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

      em.setJpaProperties(jpaProperties);


      return em;
   }
   
   @Bean
   public DataSource dataSource() {
		
		String url = "jdbc:postgresql://localhost:5432/Library";

       DriverManagerDataSource dataSource = new DriverManagerDataSource(url);
       dataSource.setDriverClassName("org.postgresql.Driver");
       dataSource.setUsername("postgres");
       dataSource.setPassword("socaacos");


       return dataSource;
   }
   

}