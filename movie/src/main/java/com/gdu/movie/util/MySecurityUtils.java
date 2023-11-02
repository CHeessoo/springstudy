package com.gdu.movie.util;

import org.springframework.stereotype.Component;

@Component
public class MySecurityUtils {


  
  // 크로스 사이트 스크립팅(Cross Site Scripting) 방지
  public String preventXSS(String source) {
    return source.replace("<", "&lt;").replace(">", "&gt;"); 
  }
  
}
