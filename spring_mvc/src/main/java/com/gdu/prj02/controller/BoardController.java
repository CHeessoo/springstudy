package com.gdu.prj02.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.prj02.dto.BoardDto;
import com.gdu.prj02.service.BoardService;
import com.gdu.prj02.service.BoardServiceImpl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  // private final BoardService boardService에 Bean이 반드시 전달됨을 보증하는 Annotation입니다.
@Controller
public class BoardController {
  
  /*
   * 요청 파라미터를 처리하는 3가지 방법 복습
   * 1. HttpServletRequest 
   * 2. @RequestParam(value="", required=false, defaultValue="0")
   * 3. 커멘드 객체
   */
  
  /*
  @RequestMapping(value="/add.do", method=RequestMethod.GET)
  public String add(HttpServletRequest request) {  // 요청 파라미터는 메소드 파라미터로 받습니다.
    int boardNo = Integer.parseInt(request.getParameter("boardNo"));
    String title = request.getParameter("title");
    System.out.println(boardNo + ", " + title);
    return "";
  }
  */
  /*
  @RequestMapping(value="/add.do", method=RequestMethod.GET)
  public String add(int boardNo, String title) {  // (@RequestParam(value="boardNo", required=false, defaultValue="0")  @RequestParam은 생략 가능
    System.out.println(boardNo + ", " + title);
    return "";
  }
  */
  /*
  @RequestMapping(value="/add.do", method=RequestMethod.GET)
  public String add(BoardDto boardDto) {  // 커멘드 객체를 이용하는 방식
    System.out.println(boardDto);
    return "";
  }
  */
  
  
  /*
   * @Autowired 하는 방법
   * 1. 필드 DI
   * 2. Setter 형식 메소드 DI
   * 3. 생성자 DI
   * 4. final처리 후 @RequiredArgsConstructor
   */  
  
  /*
     1. 필드 DI
     @Autowired  // @Service로 만들어진 Bean을 가져오세요.
     private BoardService boardService;
  */
  /*
     2. Setter 형식 메소드 DI
     private BoardService boardService;
  
     @Autowired  // @Service로 만들어진 Bean을 매개변수로 가져오세요.
     public void setBoardService(BoardService boardService) {
       this.boardService = boardService;
     }
   */
  /*
     3. 생성자 DI
     private BoardService boardService;
    
     @Autowired  // @Service로 만들어진 Bean을 매개변수로 가져오세요. (@Autowred생략 가능)
     public BoardController(BoardService boardService) {
       super();
       this.boardService = boardService;
     }
  */

  
  private final BoardService boardService;  // 변조방지를 위한 final처리
  
  /*
   * 응답
   * 1. forward는 Model에 값을 저장해서 전달한다.
   * 2. redirect는 redirectAttributes로 값을 전달할 수 있다.
   * 
   * select                   : Model 사용              - forward
   * insert / update / delete : RedirectAttributes 사용 - redirect
   */
  

  @RequestMapping(value="/add1.do", method=RequestMethod.GET)
  public String add(HttpServletRequest request, Model model) { 
    int result = boardService.add1(request);
    model.addAttribute("result", result);   // JSP로 전달하는 result 속성입니다.
    return "output";                        // output.jsp로 forward (forward는 JSP 경로를 작성합니다.)
  }


  @RequestMapping(value="/add2.do", method=RequestMethod.GET)
  public String add(int boardNo, String title, Model model) { 
    model.addAttribute("result", boardService.add2(boardNo, title));
    return "output";
  }


  @RequestMapping(value="/add3.do", method=RequestMethod.GET)
  public String add(BoardDto boardDto, Model model, RedirectAttributes redirectAttributes) { 
    int result = boardService.add3(boardDto);
    // model.addAttribute("result", result);                 // Model은 redirect할 때 값을 전달하지 못한다.
    redirectAttributes.addFlashAttribute("result", result);  // RedirectAttributes는 redirect할 때 값을 전달합니다.
    return "redirect:/output.do";                            // output.do라는 새로운 요청(redirect)을 처리하시오.
  }
  @RequestMapping(value="/output.do", method=RequestMethod.GET)
  public String output() {
    return "output";
  }

}
