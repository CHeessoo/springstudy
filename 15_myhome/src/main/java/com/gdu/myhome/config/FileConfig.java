package com.gdu.myhome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class FileConfig {

  
  @Bean
  public MultipartResolver multipartResolver() {  // file 업로드시 기본 세팅(MultipartResolver가 준비되어 있어야 Controller에서 MultipartHttpServletRequest를 사용 가능)
    CommonsMultipartResolver commonsmultipartResolver = new CommonsMultipartResolver();
    commonsmultipartResolver.setDefaultEncoding("UTF-8");
    commonsmultipartResolver.setMaxUploadSize(1024 * 1024 * 100);        // 전체 첨부 파일 최대 크기 100MB
    commonsmultipartResolver.setMaxUploadSizePerFile(1024 * 1024 * 10);  // 개별 첨부 파일 최대 크기 10MB 
    return commonsmultipartResolver;
  }
  
}
