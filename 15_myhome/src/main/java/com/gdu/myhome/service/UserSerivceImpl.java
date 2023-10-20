package com.gdu.myhome.service;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gdu.myhome.dao.UserMapper;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyJavaMailUtils;
import com.gdu.myhome.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSerivceImpl implements UserService {

  private final UserMapper userMapper;
  private final MySecurityUtils mySecurityUtils;
  private final MyJavaMailUtils myJavaMailUtils;
  
  @Override
  public void login(HttpServletRequest request, HttpServletResponse response) {  // 응답 형식이 많은 경우 void 반환 / Respose로 응답
    
    String email = request.getParameter("email");
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw")); // 사용자가 보낸 비밀번호 암호화 처리
    
    Map<String, Object> map = Map.of("email", email
                                   , "pw", pw);
    
    UserDto user = userMapper.getUser(map);
    
    if(user != null ) {                                                        // 로그인 성공시
      
      request.getSession().setAttribute("user", user);                         // 세션에 유저 정보 전달
      userMapper.insertAccess(email);                              // 접속 기록 남김
      try {
        response.sendRedirect(request.getParameter("referer") );   // 로그인 후 이전 페이지로 돌아가기
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {                                                                    // 로그인 실패시
      try {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('일치하는 회원 정보가 없습니다.')");                  // 실패 메시지 알림
        out.println("location.href='" + request.getContextPath() + "/main.do'"); // 메인 화면으로 돌아가기
        out.println("</script>");
        out.flush();
        out.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
  }
  
  
  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    
    HttpSession session = request.getSession();
    
    session.invalidate();  // 세션 초기화
    
    try {
      response.sendRedirect(request.getContextPath() + "/main.do");  // 로그아웃 후 메인페이지로 돌아가기
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  
}
