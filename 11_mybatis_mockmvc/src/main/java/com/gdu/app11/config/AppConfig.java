package com.gdu.app11.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;

@PropertySource(value="classpath:application.properties")  // @PropertySource를 가지고 프로퍼티를 읽으면 스프링이 자동으로 Environment값을 만들어서 객체(Bean)로 가지고 있음
@EnableAspectJAutoProxy  // @Aspect동작 수행
@Configuration
public class AppConfig {
  
  @Autowired  // 스프링이 가지고있는 프로퍼티 객체(Bean)을 불러서 사용하기 위해 필요하다.
  private Environment env;  // field는 Null 값
  
  // DataSource : CP(Connection Pool)을 처리하는 javax.sql.DataSource 인터페이스
  @Bean
  public DataSource dataSource() {
    // DriverManagerDataSource : CP(Connection Pool)을 처리하는 스프링 클래스
    DriverManagerDataSource dataSource = new DriverManagerDataSource();  
    dataSource.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
    dataSource.setUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
    dataSource.setUsername(env.getProperty("spring.datasource.hikari.username"));
    dataSource.setPassword(env.getProperty("spring.datasource.hikari.password"));
    return dataSource;
  }
  
  // JdbcTemplate : Jdbc를 처리하는 스프링 클래스(Connection, PreparedStatement, ResultSet 처리 담당)
  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }
  
  // TransactionManager : 트랜잭션을 처리하는 스프링 인터페이스
  @Bean
  public TransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }
  
  
}
