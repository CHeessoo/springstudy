package com.gdu.app04.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app04.vo.ArticleVo;

@Controller
public class MvcController {
  
  // DispatcherServlet(servlet-context.xml)에서 ViewResolver를 제거했으므로 JSP의 전체 경로를 모두 작성해야 한다.
  
  @RequestMapping(value="/", method=RequestMethod.GET)
  public String main() {
    
    return "/WEB-INF/main.jsp";
  }
  
  @RequestMapping(value="/write.do", method=RequestMethod.GET)
  public String write() {
    return "/WEB-INF/article/write.jsp";
  }
  
  // 1. HttpServletRequest로 요청(parameter) 받는 연습
  // 1. forwarding(전달)하는 방법 연습 : 데이터를 저장할 때 사용하는 Model (변수 자체로 사용)
  // @RequestMapping(value="/register.do", method=RequestMethod.POST)
  public String register(HttpServletRequest request, Model model) {
    int articleNo = Integer.parseInt(request.getParameter("articleNo"));
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    model.addAttribute("articleNo", articleNo);
    model.addAttribute("title", title);
    model.addAttribute("content", content);
    return "/WEB-INF/article/result.jsp";
  }
  
  // 2. @RequestParam을 이용하는 방법 연습
  // @RequestParam(value="")는 생략 가능
  // 2. forwarding 연습 : Model을 객체 단위로 저장해서 사용
  // @RequestMapping(value="/register.do", method=RequestMethod.POST)
  public String register2(@RequestParam(value="articleNo") int articleNo
                        , @RequestParam(value="title") String title
                        , @RequestParam(value="content") String content
                        , Model model) {
    ArticleVo vo = new ArticleVo(articleNo, title, content);
    model.addAttribute("vo", vo);
    return "/WEB-INF/article/result.jsp";
  }
  
  // 3. 커맨드 객체(VO(DTO))를 통해서 알아서 받는 방법 연습
  // 3. 커맨드 객체를 이용하는 방식 - Model을 선언하고 저장할 필요가 없다.
  //    커맨드 객체 특징 : 코드가 없어도 Model에 자동으로 저장 됨(전달되는건 객체명(vo)가 아닌 클래스명(타입의 이름인 ArticleVo)으로 저장돼서 전달한다.)
  // @RequestMapping(value="/register.do", method=RequestMethod.POST)
  public String register3(ArticleVo vo) {
    return "/WEB-INF/article/result.jsp";
  }
  
  // 4. @ModelAttribute(value="") : 커맨드 객체를 이용할때 Model에 저장되는 이름 지정(변경)하는 연습
  @RequestMapping(value="/register.do", method=RequestMethod.POST)
  public String register4(@ModelAttribute(value="atcVo") ArticleVo vo) {
    return "/WEB-INF/article/result.jsp";
  }

}
