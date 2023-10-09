package com.gdu.prj01.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.gdu.prj01.config.AppConfig;
import com.gdu.prj01.dto.BoardDto;

public class MainWrapper {

  public static void main(String[] args) {
    
    AbstractApplicationContext ctx = new GenericXmlApplicationContext("com/gdu/prj01/app-context.xml");
    BoardDto boardDto1 = ctx.getBean("board1", BoardDto.class);  // 타입 지정 방식  // 타입 작성시 뒤에 클래스를 붙여줘야한다.
    System.out.println(boardDto1);
    BoardDto boardDto2 = (BoardDto)ctx.getBean("board2");        // 캐스팅 방식
    System.out.println(boardDto2);
    ctx.close();
    
    
    AbstractApplicationContext ctx2 = new AnnotationConfigApplicationContext(AppConfig.class);
    BoardDto boardDto3 = ctx2.getBean("board3", BoardDto.class);
    System.out.println(boardDto3);
    ctx2.close();

  }

}
