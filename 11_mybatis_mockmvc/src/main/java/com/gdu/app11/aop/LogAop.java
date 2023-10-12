package com.gdu.app11.aop;

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

@Slf4j
@Aspect
@Component
public class LogAop {
  
  // @Before("execution()")과 같이 포인트컷과 어드바이스를 분리하지 않는 방법도 가능하다.

  // 포인트컷 : 어떤 메소드에서 동작하는지 표현식으로 작성
  @Pointcut("execution(* com.gdu.app11.controller.*Controller.*(..))")
  public void setPointCut() {}
  
  // 어드바이스 : 포인트컷에서 실제로 동작할 내용
  @Before("setPointCut()") // 포인트컷이 등록된 함수 호출
  public void doLog(JoinPoint joinPoint) {  // JoinPoint : 어드바이스로 전달되는 메소드 (JoinPoint 안에 Pointcut이 포함된 관계)
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
