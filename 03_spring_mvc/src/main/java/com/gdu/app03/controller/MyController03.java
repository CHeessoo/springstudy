package com.gdu.app03.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController03 {
  
  /*
   * 1. HttpServletRequest request를 이용한 요청 파라미터 처리
   *  1) Java EE 표준 방식이다. (사용 권장)
   *  2) 파라미터뿐만 아니라 HttpSession session, String contextPath 와 같은 정보도 꺼낼 수 있으므로 여전히 강력한 도구이다. (요청 ip등 많은것을 알 수 있는 방식)
   */
  
  /*
   * Model 인터페이스
   * 1. Spring에서 forwarding할 정보를 저장하는 곳이다.
   * 2. 메소드
   *  1) addAttribute : Model에 속성을 추가하는 메소드
   * 
   * 3. Model Attribute를 사용해서 forward하는 정보를 Model에 저장하는 것이 권고사항이나,
   *    실제 내부 로직(동작)은 request를 이용해서 attribute를 저장한다.
   */
  
  
  // @RequestMapping("/blog/detail.do")  // GET 방식의 method는 생략할 수 있다. value만 작성할 땐 value= 부분도 생략할 수 있다.
  public String blogDetail(HttpServletRequest request, Model model) {  // request에는 요청만 저장하고 forward할 정보는 Model에 저장한다.     
    // ViewResolver의 prefix : /WEB-INF/views/
    // ViewResolver의 suffix : .jsp
    String blogNo = request.getParameter("blogNo"); // 파라미터를 받아옴  
    model.addAttribute("blogNo", blogNo);
    return "blog/detail";   // /WEB-INF/views/blog/detail.jsp  forward한다.
  }
  
  /*
   * 2. @RequestParam을 이용한 요청 파라미터 처리
   *  1) 파라미터의 개수가 적은 경우에 유용하다.
   *  2) 주요 메소드
   *    (1) value        : 요청 파라미터의 이름
   *    (2) required     : 요청 파라미터의 필수 여부(디폴트 true - 요청 파라미터가 없으면 오류(500번대) 발생)
   *    (3) defaultValue : 요청 파라미터가 없는 경우에 사용할 값
   *  3) @RequestParam을 생략할 수 있다.
   *    @RequestParam(value="blogNo") int blogNo  == int blogNo
   */
  
  
  @RequestMapping("/blog/detail.do")
  public String blogDetail2(@RequestParam(value="blogNo",required=false, defaultValue="1") int blogNo, Model model) {  // blogNo로 전달되는 파라미터를 int blogNo 변수에 저장(정수로 자동 변환)
                                                                                                                       // blogNo 값이 전달되지 않으면(필수가 아닌 경우) 1을 전달하겠다.(defaultValue 동작)
    model.addAttribute("blogNo", blogNo);
    return "blog/detail";
  }

}
