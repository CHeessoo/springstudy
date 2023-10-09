package com.gdu.app03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController02 {
  
  // Spring 4 버전 이후 사용 가능한 @RequestMapping
  // 1. @GetMapping
  // 2. @PostMapping
  
  @GetMapping(value="/notice/list.do")
  public String noticeList() {
    // ViewResolver의 prefix : /WEB-INF/views/
    // ViewResolver의 suffix : .jsp
    return "notice/list"; //   /WEB-INF/views/noice/list.jsp
  }
  
  // 반환이 없는 경우에는 요청 주소를 Jsp 경로로 인식한다.
  // /member/list.do 요청을 /member.list.jsp 경로로 인식한다.
  // /member/list    요청을 /member.list.jsp 경로로 인식한다. (suffix값 .do는 안 붙여도 상관없다. Jsp servlet(Controller)에서 먼저 나온 개념이라 관습처럼 주소를 구분하기 위해 사용된다.)
  @GetMapping(value={"/member/list.do", "/member/list"})
  public void memberList() {  
    // void(반환타입이 없는 경우)는 1차로 suffix에서 경로를 찾으려고 하고 
    // suffix가 없는 경우 주소를 Jsp경로로 바꿔서 인식한다. (없을시 404)    
  }

}
