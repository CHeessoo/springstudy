package com.gdu.app01.xml01;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainWrapper {

  public static void main(String[] args) {
    
    // app-context.xml 파일 읽기 (여기에서 <bean> 태그로 정의해 둔 객체가 생성된다.)
    AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml01/app-context.xml");
    
    // <bean> 태그로 정의된 객체 가져오기
    Calculator calculator = (Calculator)ctx.getBean("calc");  // ctx.getBean("calc", Calculator.class) 코드도 동일함
    
    // 객체 사용해 보기
    calculator.add(1, 2);
    calculator.sub(3, 4);
    calculator.mul(5, 6);
    calculator.div(7, 8);
    
    // app-context.xml 파일 닫기
    ctx.close();

  }

}
