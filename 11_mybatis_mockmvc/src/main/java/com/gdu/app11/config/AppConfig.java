package com.gdu.app11.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@PropertySource(value="classpath:application.properties")  // @PropertySource를 가지고 프로퍼티를 읽으면 스프링이 자동으로 Environment값을 만들어서 객체(Bean)로 가지고 있음
@EnableAspectJAutoProxy  // @Aspect동작 수행
@Configuration
public class AppConfig {
  
  @Autowired  // 스프링이 가지고있는 프로퍼티 객체(Bean)를 불러서 사용하기 위해 필요하다.
  private Environment env;  // field는 Null 값
  
  // HikariConfig : HikariCP를 이용해 DB에 접속할 때 필요한 정보를 처리하는 Hikari 클래스
  @Bean
  public HikariConfig hikariConfig() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
    hikariConfig.setJdbcUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
    hikariConfig.setUsername(env.getProperty("spring.datasource.hikari.username"));
    hikariConfig.setPassword(env.getProperty("spring.datasource.hikari.password"));
    
    return hikariConfig;
  }
  
  // HikariDataSource : CP(Connection Pool)을 처리하는 Hikari 클래스
  @Bean
  public HikariDataSource hikariDataSource() {
    return new HikariDataSource(hikariConfig());
  }
  
  // SqlSessionFactory : SqlSessionTemplate을 만들기위한 mybatis 인터페이스
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(hikariDataSource());
    sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(env.getProperty("mybatis.config-location")));
    sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
    return sqlSessionFactoryBean.getObject();
  }
  
  // SqlSessionTemplate : 쿼리 실행을 담당하는 mybatis 클래스
  @Bean
  public SqlSessionTemplate sqlSessionTemplate() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory());
  }
  
  // TransactionManager : 트랜잭션을 처리하는 스프링 인터페이스
  @Bean
  public TransactionManager transactionManager() {
    return new DataSourceTransactionManager(hikariDataSource());
  }
  
  
}
