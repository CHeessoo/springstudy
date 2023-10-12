package com.gdu.app12.intercept;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component  // 객체(Bean)으로 만들기
public class MyInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<script>");
    out.println("alert('인터셉터가 동작했습니다.')");
    out.println("history.back()");  // history.back() : 전단계로 돌려보냄
    out.println("</script>");
    out.flush();
    out.close();
    
    return false; // 컨트롤러의 요청이 처리되지 않는다.
    
  }
  
}
