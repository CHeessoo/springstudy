package com.gdu.app10.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class BeforeAop {
  
  // 포인트컷 : 언제 동작하는가?
  @Pointcut("execution(* com.gdu.app10.controller.ContactController.*(..))")  // ContactController의 모든 메소드에서 동작한다.
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
    log.info("Before 어드바이스 동작");
  }
  
}
