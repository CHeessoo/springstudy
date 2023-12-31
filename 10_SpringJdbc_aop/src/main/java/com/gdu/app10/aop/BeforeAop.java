package com.gdu.app10.aop;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j  // private static final Logger log = LoggerFactory.getLogger(BeforeAop.class);
@Aspect
@Component
public class BeforeAop {
  
  // 포인트컷 : 언제 동작하는가?
  @Pointcut("execution(* com.gdu.app10.controller.*Controller.*(..))")  // Controller로 끝나는 모든 컨트롤러의 모든 메소드에서 동작한다.
  public void setPointCut() { }  // 이름만 제공하는 메소드(이름은 마음대로 본문도 필요 없다.)
  
  // 어드바이스 : 무슨 동작을 하는가?
  @Before("setPointCut()")  // ContactController의 모든 메소드가 동작하는 시점 이전에 동작한다.
  public void beforeAdvice(JoinPoint joinPoint) {
    
    /*
     * Before 어드바이스
     * 1. 반환타입 : void
     * 2. 메소드명 : 마음대로
     * 3. 매개변수 : JoinPoint
     */
    
    /* ContactController의 모든 메소드가 동작하기 전에 요청(방식/주소/파라미터) 출력하기 */
    
    // 1. HttpServletRequest
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = servletRequestAttributes.getRequest();  // 리퀘스트를 구함
    
    // 2. 요청 파라미터 -> Map 변환
    Map<String, String[]> map = request.getParameterMap();
    
    // 3. 요청 파라미터 출력 형태 만들기
    String params = "";
    if(map.isEmpty()) {
      params += "No Parameter";
    } else {
      for(Map.Entry<String, String[]> entry : map.entrySet()) {
        params += entry.getKey() + ":" + Arrays.toString(entry.getValue()) + " ";  // 파라미터 이름 + 파라미터 값을 출력
      }
    }
    
    // 4. 로그 찍기 (치환 문자 {} 활용)
    log.info("{} {}", request.getMethod(), request.getRequestURI());  // 요청 방식, 요청 주소
    log.info("{}", params);                                           // 요청 파라미터
    
  }
  
}
