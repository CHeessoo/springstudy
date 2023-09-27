package com.gdu.app03.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController03 {
  
  @RequestMapping("/blog/detail.do")  // GET 방식의 method는 생략할 수 있다. value만 작성할 땐 value= 부분도 생략할 수 있다.
  public String blogDetail(HttpServletRequest request) {  // HttpServletRequest : 파라미터를 받을 수 있는 여러 방법들이 있지만 사용 권장하는 방식
    // ViewResolver의 prefix : /WEB-INF/views/
    // ViewResolver의 suffix : .jsp
    String blogNo = request.getParameter("blogNo");       // 파라미터를 받아옴
    request.setAttribute("blogNo", blogNo);               // forward 할 때 request에 정보를 저장하는 방법
    return "/blog/detail";  // /WEB-INF/views/blog/detail.jsp  forward한다.
  }

}
