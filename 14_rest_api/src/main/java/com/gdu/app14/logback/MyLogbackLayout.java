package com.gdu.app14.logback;

import java.text.SimpleDateFormat;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

public class MyLogbackLayout extends LayoutBase<ILoggingEvent> {
  
  @Override
  public String doLayout(ILoggingEvent event) { // event 안에 로그 전달 정보들이 들어있음
    
    // <pattern>을 대신할 수 있는 자바코드
    
    StringBuilder sb = new StringBuilder();
    
    sb.append("[");
    sb.append(new SimpleDateFormat("HH:mm:ss").format(event.getTimeStamp()));  // 로그 시간 형식을 SimpleDateFormat에 맞춰서 출력
    sb.append("] ");
    
    sb.append(String.format("%-5s", event.getLevel()));                        // 5자리 문자열을 왼쪽 정렬로 출력, 로그 레벨
    sb.append(":");
    String loggerName = event.getLoggerName(); // 로그 이름 변수처리해서 빼줌
    sb.append(loggerName);
    if(loggerName.equals("jdbc.sqlonly")) {    // 로그 네임이 sqlonly이면
      sb.append("\n");                         // 줄바꿈을 하겠다.
    } else {
      sb.append(" - ");                        // 아니면 하이픈(-)으로 연결하겠다.      
    } 
    sb.append(event.getFormattedMessage());    // 메세지
    sb.append("\n");
    
    return sb.toString();
  }
  
}
